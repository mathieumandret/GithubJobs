package com.example.githubjobs.ui.positions.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.githubjobs.data.PositionsRepository
import com.example.githubjobs.data.local.model.Position
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionDetailsViewModel(app: Application) : AndroidViewModel(app), KoinComponent {
    private val repository: PositionsRepository by inject()

    val positionId = MutableLiveData<String>()

    val position = Transformations.switchMap(positionId) {
        repository.getPositionById(it)
    }

    fun updatePosition(position: Position) {
        repository.update(position)
    }
}