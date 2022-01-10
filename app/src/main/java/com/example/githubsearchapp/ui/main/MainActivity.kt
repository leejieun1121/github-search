package com.example.githubsearchapp.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.githubsearchapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainPagingAdapter: MainPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainPagingAdapter = MainPagingAdapter()
        setupViews()
    }

    private fun setupViews() {
        binding.rvMain.adapter = mainPagingAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { mainPagingAdapter.retry() }
        )
        mainPagingAdapter.addLoadStateListener { loadState ->
            binding.tvNothing.isVisible = loadState.refresh is LoadState.NotLoading && mainPagingAdapter.itemCount == 0
        }

        val editTextObservable = Observable.create { emitter: ObservableEmitter<String>? ->
            binding.etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.toString().isEmpty()) {
                        mainPagingAdapter.submitData(lifecycle, PagingData.empty())
                    }
                    emitter?.onNext(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }.debounce(500, TimeUnit.MICROSECONDS)
            .subscribeOn(Schedulers.io())

        editTextObservable.subscribe { query -> search(query) }
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            mainPagingAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvMain.scrollToPosition(0) }
        }

        lifecycleScope.launch {
            viewModel.getRepoList(query).collectLatest {
                mainPagingAdapter.submitData(it)
            }
        }
    }
}