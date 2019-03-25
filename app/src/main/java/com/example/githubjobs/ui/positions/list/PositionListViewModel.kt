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

    // Valeur de recherche
    private val searchFilter = MutableLiveData("")
    // Si le filtre de favoris est activé ou non
    private val favoritesOnly = MutableLiveData(false)
    // LiveData composite, combinaison du filtre de recherche et de favoris
    private val filters = MediatorLiveData<Pair<Boolean, String>>()
    // Liste des posititions envoyée à l'activité en fonction des filtres
    private var displayedPositions: LiveData<List<Position>>

    private val loading: MutableLiveData<Boolean> = MutableLiveData()

    // Référence vers la requête de telechargment, permet de l'annuler et d'éviter les fuites mémoires
    // quand le viewmodel est detruit
    private var positionsQuery: Disposable? = null

    init {
        // Combiner les filtres en un seul
        filters.addSource(favoritesOnly) { filters.value = combine(favoritesOnly, searchFilter) }
        filters.addSource(searchFilter) { filters.value = combine(favoritesOnly, searchFilter) }
        // La liste des positions est une fonctions des filtres, évaluée à chaque fois que l'un des filtres
        // change
        displayedPositions =
                // Appliquer le filtre de recherche de recherche
            Transformations.switchMap(filters) {
                val searchFilterValue = it.second
                val filteredList = if (searchFilterValue.isEmpty())
                // Si il est vide, renvoyer toutes les positions
                    positionsRepository.getAllPositions()
                else
                // Sinon faire un recherche par ce filtre
                    positionsRepository.findPositionByTitle(searchFilterValue)
                // Si le filtre par favori est active, ne laisser passer que les favoris
                return@switchMap if (it.first) Transformations.map(filteredList) { pos ->
                    pos.filter { p -> p.isFavorite }
                } else filteredList
            }
    }

    /* Combine les valeurs des filtres en une paire */
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
            .doOnSubscribe { loading.value = true }
            .doOnTerminate { loading.value = false }
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

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

}