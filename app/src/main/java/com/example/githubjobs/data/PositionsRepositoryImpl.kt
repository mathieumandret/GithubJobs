package com.example.githubjobs.data

import com.example.githubjobs.data.local.dao.PositionDAO
import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.data.remote.api.PositionAPI
import com.example.githubjobs.data.remote.converters.PositionConverter
import com.example.githubjobs.data.remote.model.PositionResponse
import io.reactivex.Single
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PositionsRepositoryImpl : PositionsRepository, KoinComponent {

    private val dao: PositionDAO by inject()
    private val api: PositionAPI by inject()
    private val converter = PositionConverter()

    private fun cachedListQuery(
        fromCache: Single<List<Position>>,
        fromAPI: Single<List<PositionResponse>>
    ): Single<List<Position>> =
    // If the list from the DB is empty, try to get the positions from the api and save the result
        fromCache.flatMap { if (it.isEmpty()) fromAPI.convertAllAndSave() else Single.just(it) }


    private fun cachedQuery(
        fromCache: Single<Position>,
        fromAPI: Single<PositionResponse>
    ): Single<Position> =
    // If the position is not found in cache, try to  get is from the api
        fromCache.onErrorResumeNext { fromAPI.convertAndSave() }


    override fun getAllPositions(): Single<List<Position>> =
        cachedListQuery(dao.getAllPositions(), api.getAllPositions())


    override fun getPositionById(id: String): Single<Position> =
        cachedQuery(
            dao.getPositionById(id),
            api.getPositionById(id)
        )

    override fun findPositionsByDescription(description: String): Single<List<Position>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findPositionsByLocation(location: String): Single<List<Position>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findPositionsByLocationAndDescription(location: String, description: String): Single<List<Position>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun Single<List<PositionResponse>>.convertAllAndSave(): Single<List<Position>> =
        this.toObservable()
            .flatMapIterable { it }
            .map(converter::convert)
            .toList()
            .doOnSuccess(dao::insertAll)

    private fun Single<PositionResponse>.convertAndSave(): Single<Position> =
        this.map(converter::convert).doOnSuccess(dao::insert)
}