package com.farid.coroutine.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getMoviesList() = getResult { apiService.getMoviesList() }

    suspend fun getMovieDetail(imdbId:String) = getResult { apiService.getMovieDetail(imdbId = imdbId) }

}