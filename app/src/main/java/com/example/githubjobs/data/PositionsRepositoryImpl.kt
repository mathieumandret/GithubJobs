package com.example.githubjobs.data

import androidx.lifecycle.LiveData
import com.example.githubjobs.data.local.dao.PositionDAO
import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.data.remote.api.PositionAPI
import com.example.githubjobs.data.remote.converters.PositionConverter
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
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
            .map { it.map(converter::convert) }
            .doOnSuccess(dao::insertAll)
        )


    override fun findPositionsByDescription(description: String): LiveData<List<Position>> {
        return dao.findPositionsByDescription(description)
    }

    override fun findPositionByTitle(title: String): LiveData<List<Position>> {
        return dao.findPositionByTitle("%$title%")
    }


    override fun findPositionsByLocation(location: String): LiveData<List<Position>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findPositionsByLocationAndDescription(
        location: String,
        description: String
    ): LiveData<List<Position>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}