package com.example.githubjobs.ui.positions.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubjobs.data.PositionsRepository
import com.example.githubjobs.data.local.model.Position
import io.reactivex.disposables.Disposable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionListViewModel(app: Application) : AndroidViewModel(app), KoinComponent {
    private val positionsRepository: PositionsRepository by inject()
    val positions: LiveData<List<Position>> = positionsRepository.getAllPositions()
    private var positionsQuery: Disposable? = null

    fun refreshPositions(onSuccess: () -> Unit, onError: (err: Throwable) -> Unit) {
        positionsQuery?.dispose()
        positionsQuery = positionsRepository.downloadPositions()
            .subscribe(onSuccess, onError)
    }

}