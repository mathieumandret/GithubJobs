package com.example.githubjobs.ui.positions.details

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.githubjobs.R
import com.example.githubjobs.databinding.ActivityPositionDetailsBinding
import com.example.githubjobs.ui.base.BaseActivity
import org.koin.standalone.inject

class PositionDetailsActivity : BaseActivity<PositionDetailsViewModel, ActivityPositionDetailsBinding>() {


    companion object {
        const val POSITION_ID = "id"
    }

    override val viewModel: PositionDetailsViewModel by inject()
    override val layoutId = R.layout.activity_position_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        val id = intent.getStringExtra(POSITION_ID)
        viewModel.positionId.value = id
        viewModel.position.observe(this, Observer {
            println(it)
        })
    }
}
