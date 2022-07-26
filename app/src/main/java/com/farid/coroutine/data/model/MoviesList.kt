package com.farid.coroutine.data.model
import com.google.gson.annotations.SerializedName

data class MoviesList(
    @SerializedName("Search")
    val search: List<Movie>
)