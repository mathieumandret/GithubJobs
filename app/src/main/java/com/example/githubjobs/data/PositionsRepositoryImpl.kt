package com.example.githubjobs.data

import androidx.lifecycle.LiveData
import com.example.githubjobs.data.local.dao.PositionDAO
import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.data.remote.api.PositionAPI
import com.example.githubjobs.data.remote.converters.PositionConverter
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionsRepositoryImpl : PositionsRepository, KoinComponent {

    private val dao: PositionDAO by inject()
    private val api: PositionAPI by inject()
    private val converter = PositionConverter()

    override fun getAllPositions(): LiveData<List<Position>> = dao.getAllPositions()


    override fun getPositionById(id: String): LiveData<Position> = dao.getPositionById(id)

    override fun downloadPositions(): Completable =
        Completable.fromSingle(api.getAllPositions()
            .subscribeOn(Schedulers.io())
            // Convert all the position from the list
            .toObservable()
            .flatMapIterable { it }
            .map(converter::convert)
            .map(this::updateIfExisting)
            .toList()
            .doOnSuccess(dao::insertAll)
        )

    private fun updateIfExisting(position: Position): Position {
        // La position existe peut être déjà, on peut se permettre de la récuperer
        // de manière synchrone puisque cette fonction est appellée dans un chaine
        // d'observable qui s'éxecute sur le pool de thread IO, on ne bloque pas la UI
        dao.getPositionByIdBlocking(position.id)?.let {
            // Conserver le statut de favori de la position existante
            position.isFavorite = it.isFavorite
        }
        return position
    }

    override fun update(position: Position) {
        doAsync { dao.update(position) }
    }


    override fun findPositionByTitle(title: String): LiveData<List<Position>> {
        return dao.findPositionByTitle("%$title%")
    }

}