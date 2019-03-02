package com.example.githubjobs.ui.positions.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.githubjobs.BR
import com.example.githubjobs.R
import com.example.githubjobs.databinding.ActivityPositionListBinding
import kotlinx.android.synthetic.main.activity_position_list.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionListActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityPositionListBinding

    private val viewModel: PositionListViewModel by inject()

    private val adapter = PositionsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_position_list)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
        setUpAdapter()
        setUpRecyclerView()
        setUpSwipeRefreshLayout()
    }

    private fun setUpAdapter() {
        viewModel.positions.observe(this, Observer { adapter.submitList(it) })
    }

    private fun setUpRecyclerView() {
        binding.list.adapter = adapter
    }

    private fun refreshPositionList() {
        viewModel.refreshPositions(
            this::onRefresh,
            this::onRefreshError
        )
    }

    private fun setUpSwipeRefreshLayout() {
        swipeLayout.setOnRefreshListener {
            refreshPositionList()
        }
    }

    private fun onRefreshError(err: Throwable) {
        err.printStackTrace()
        swipeLayout.isRefreshing = false
    }

    private fun onRefresh() {
        swipeLayout.isRefreshing = false
        println("KEK")
    }
}
