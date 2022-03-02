package com.example.githubsearchapp.presenter

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchapp.R
import com.example.githubsearchapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@FlowPreview
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imm: InputMethodManager
    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainPagingAdapter: MainPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this@MainActivity

        imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        mainPagingAdapter = MainPagingAdapter()
        setupViews()
    }

    private fun setupViews() {
        binding.rvMain.adapter = mainPagingAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { mainPagingAdapter.retry() }
        )

        binding.rvMain.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                binding.etSearch.clearFocus()
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

        mainPagingAdapter.addLoadStateListener { loadState ->
            binding.tvNothing.isVisible =
                loadState.refresh is LoadState.NotLoading && mainPagingAdapter.itemCount == 0 && binding.etSearch.text.isNotEmpty()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainPagingAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { binding.rvMain.scrollToPosition(0) }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                textChanges()
                    .debounce(500)
                    .collectLatest { keyword ->
                        viewModel.getRepoList(keyword)
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.repoList.collectLatest { repoInfo ->
                    mainPagingAdapter.submitData(repoInfo)
                }
            }
        }

    }

    private fun textChanges() = callbackFlow {
        val listener = binding.etSearch.doAfterTextChanged { keyword ->
            trySend(keyword.toString())
        }
        awaitClose { binding.etSearch.removeTextChangedListener(listener) }
    }

}