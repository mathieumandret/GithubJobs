package com.example.githubjobs.ui.positions.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.githubjobs.R
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionListActivity : AppCompatActivity(), KoinComponent {

    private val viewModel: PositionListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position_list)
        viewModel.positions.observe(this, Observer {
            println(it)
        })

    }
}
