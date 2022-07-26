package com.farid.coroutine.data.remote

import com.farid.coroutine.data.model.MovieDetail
import com.farid.coroutine.data.model.MoviesList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun getMoviesList(@Query("apikey") apiKey:String = "3e974fca" , @Query("s") seriesName:String = "batman") : Response<MoviesList>

    @GET("/")
    suspend fun getMovieDetail(@Query("apikey") apiKey:String = "3e974fca" , @Query("i") imdbId:String) : Response<MovieDetail>

}