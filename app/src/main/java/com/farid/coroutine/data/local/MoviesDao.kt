package com.farid.coroutine.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farid.coroutine.data.model.Movie
import com.farid.coroutine.data.model.MovieDetail

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MOVIES_TABLE")
    fun getAllMovies() : LiveData<List<Movie>>

    @Query("SELECT * FROM MOVIE_DETAIL_TABLE WHERE imdbID = :imdbId")
    fun getMovieDetail(imdbId : String) : LiveData<MovieDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies : List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetail)

}