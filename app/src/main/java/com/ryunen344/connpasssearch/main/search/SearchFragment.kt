package com.ryunen344.connpasssearch.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ryunen344.connpasssearch.R
import com.ryunen344.connpasssearch.behavior.EndlessScrollListener
import com.ryunen344.connpasssearch.databinding.FragmentSearchBinding
import com.ryunen344.connpasssearch.util.LogUtil
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by sharedViewModel()

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtil.d()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        LogUtil.d()
        super.onActivityCreated(savedInstanceState)


        val layoutManager = LinearLayoutManager(context)

        main_event_list.apply {
            this.layoutManager = layoutManager
            this.setHasFixedSize(true)
            this.adapter = SearchAdapter(searchViewModel)
            this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            this.addOnScrollListener(object : EndlessScrollListener(layoutManager) {
                override fun onLoadMore(currentPage: Int) {
                    LogUtil.d()
                    //searchViewModel.loadMoreEventList(currentPage)
                }

            })
        }

        swipe_refresh.apply {
            setOnRefreshListener {
                LogUtil.d()
                isRefreshing = false
            }
        }

        searchViewModel.keyword.observe(this, Observer {
            if (it.isNotEmpty()) {
                searchViewModel.searchEvent(it)
            }
        })

        binding.viewModel = searchViewModel
        searchViewModel.onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogUtil.d()
        super.onViewCreated(view, savedInstanceState)
    }


}