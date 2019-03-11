package com.example.githubjobs.ui.positions.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.githubjobs.data.PositionsRepository
import com.example.githubjobs.data.local.model.Position
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionListViewModel(app: Application) : AndroidViewModel(app), KoinComponent {
    private val positionsRepository: PositionsRepository by inject()
    private val searchFilter = MutableLiveData<String>()
    var displayedPositions: LiveData<List<Position>>
    private var positionsQuery: Disposable? = null

    init {
        displayedPositions = Transformations.switchMap(searchFilter) {
            if (it.isEmpty())
                positionsRepository.getAllPositions()
            else
                positionsRepository.findPositionByTitle(it)
        }
        searchFilter.value = ""
    }

    fun refreshPositions(onSuccess: () -> Unit, onError: (err: Throwable) -> Unit) {
        positionsQuery?.dispose()
        positionsQuery = positionsRepository.downloadPositions()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)
    }

    fun search(key: String) {
        searchFilter.value = key
    }

}