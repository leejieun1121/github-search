package com.example.githubsearchapp.search.presenter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchapp.R
import com.example.githubsearchapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var imm: InputMethodManager
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var mainPagingAdapter: MainPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imm =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        mainPagingAdapter = MainPagingAdapter()

        setupViews()
        collectFlows()
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

    }

    private fun collectFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    mainPagingAdapter.loadStateFlow
                        .distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { binding.rvMain.scrollToPosition(0) }
                }

                launch {
                    textChanges()
                        .debounce(500)
                        .collectLatest { keyword ->
                            viewModel.getRepoList(keyword)
                        }
                }

                launch {
                    viewModel.repoList.collectLatest { repoInfo ->
                        mainPagingAdapter.submitData(repoInfo)
                    }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}