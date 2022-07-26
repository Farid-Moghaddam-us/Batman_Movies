package com.farid.coroutine.data.repository

import com.farid.coroutine.data.local.MoviesDao
import com.farid.coroutine.data.remote.BaseDataSource
import com.farid.coroutine.data.remote.RemoteDataSource
import com.farid.coroutine.utils.performGetOperation
import javax.inject.Inject

class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource : MoviesDao) :
    BaseDataSource() {

    fun getMovieDetail(imdbId: String) = performGetOperation(
        databaseQuery = { localDataSource.getMovieDetail(imdbId) },
        networkCall = { remoteDataSource.getMovieDetail(imdbId) },
        saveCallResult = { localDataSource.insertMovieDetail(it) }
    )

    fun getMoviesList() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getMoviesList() },
        saveCallResult = { localDataSource.insertAllMovies(it.search) }
    )
}