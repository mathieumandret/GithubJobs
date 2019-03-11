package com.example.githubjobs.ui.positions.details

import android.os.Bundle
import android.view.Menu
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val favoriteMenuItem = menu?.findItem(R.id.favorite)
        viewModel.position.observe(this, Observer {
            favoriteMenuItem?.setIcon(
                if (it.isFavorite) R.drawable.ic_star else R.drawable.ic_star_border
            )

        })
        favoriteMenuItem?.setOnMenuItemClickListener {
            if (viewModel.position.value == null) return@setOnMenuItemClickListener false
            val currentPosition = viewModel.position.value!!
            currentPosition.isFavorite = !currentPosition.isFavorite
            viewModel.updatePosition(currentPosition)
            return@setOnMenuItemClickListener true
        }
        return true
    }
}
