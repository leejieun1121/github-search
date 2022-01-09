package com.example.githubsearchapp.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.githubsearchapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.collect
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
        binding.rvMain.adapter = mainPagingAdapter
        val deleteKeyObservable = Observable.create { emitter: ObservableEmitter<String>? ->
            binding.etSearch.setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_DEL)
                    emitter?.onNext(binding.etSearch.text.toString())
                false
            }
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
                    } else {
                        emitter?.onNext(s.toString())
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }.debounce(500, TimeUnit.MICROSECONDS)
            .subscribeOn(Schedulers.io())

        editTextObservable.subscribe { query -> search(query) }
        deleteKeyObservable.subscribe { query -> search(query) }
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            viewModel.getRepoList(query).collect {
                mainPagingAdapter.submitData(it)
            }
        }
    }

}