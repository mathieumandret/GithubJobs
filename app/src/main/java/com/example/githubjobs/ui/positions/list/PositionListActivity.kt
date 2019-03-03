package com.example.githubjobs.ui.positions.list

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.githubjobs.R
import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.databinding.ActivityPositionListBinding
import com.example.githubjobs.ui.base.BaseActivity
import com.example.githubjobs.ui.positions.details.PositionDetailsActivity
import kotlinx.android.synthetic.main.activity_position_list.*
import org.jetbrains.anko.intentFor
import org.koin.standalone.inject

class PositionListActivity : BaseActivity<PositionListViewModel, ActivityPositionListBinding>() {

    override val viewModel: PositionListViewModel by inject()
    override val layoutId = R.layout.activity_position_list

    private val adapter = PositionsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
        setUpRecyclerView()
        setUpSwipeRefreshLayout()
    }

    private fun setUpAdapter() {
        viewModel.positions.observe(this, Observer { adapter.submitList(it) })
        adapter.setOnItemClickListener(this::showPositionDetails)
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
    }

    private fun showPositionDetails(position: Position) {
        startActivity(
            intentFor<PositionDetailsActivity>(
                PositionDetailsActivity.POSITION_ID to position.id
            )
        )
    }
}
