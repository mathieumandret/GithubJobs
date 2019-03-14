package com.example.githubjobs.ui.positions.list

import android.app.Application
import androidx.lifecycle.*
import com.example.githubjobs.data.PositionsRepository
import com.example.githubjobs.data.local.model.Position
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionListViewModel(app: Application) : AndroidViewModel(app), KoinComponent {
    private val positionsRepository: PositionsRepository by inject()
    private val searchFilter = MutableLiveData("")
    private val favoritesOnly = MutableLiveData(false)
    private val displayed = MediatorLiveData<Pair<Boolean, String>>()
    private var displayedPositions: LiveData<List<Position>>
    private var positionsQuery: Disposable? = null

    init {
        displayed.addSource(favoritesOnly) { displayed.value = combine(favoritesOnly, searchFilter) }
        displayed.addSource(searchFilter) { displayed.value = combine(favoritesOnly, searchFilter) }
        displayedPositions =
            Transformations.switchMap(displayed) {
                val filter = it.second
                val filteredList = if (filter.isEmpty())
                    positionsRepository.getAllPositions()
                else
                    positionsRepository.findPositionByTitle(filter)
                return@switchMap if (it.first) Transformations.map(filteredList) { pos -> pos.filter { p -> p.isFavorite } } else filteredList
            }
    }

    private fun combine(
        favorites: LiveData<Boolean>,
        searchFilter: LiveData<String>
    ): Pair<Boolean, String> {
        val fav = favorites.value!!
        val filter = searchFilter.value!!
        return Pair(fav, filter)
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

    fun resetSearch() {
        searchFilter.value = ""
    }

    fun getPositions(): LiveData<List<Position>> {
        return displayedPositions
    }

    // Utiliser un getter pour expose un LiveData et pas un MutableLiveData
    fun getFavoritesOnly(): LiveData<Boolean> {
        return favoritesOnly
    }

    fun toggleFavoritesOnly() {
        favoritesOnly.value = favoritesOnly.value!!.not()
    }

}