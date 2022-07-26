package com.farid.coroutine.ui.movies_detail

import androidx.lifecycle.*
import com.farid.coroutine.utils.Resource
import com.farid.coroutine.data.repository.Repository
import com.farid.coroutine.data.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    lateinit var mainNavigator: WeakReference<MovieDetailNavigator>
    private val _imdbId = MutableLiveData<String>()

    private val _movieDetail = _imdbId.switchMap { imdbId ->
        repository.getMovieDetail(imdbId)
    }
    val movieDetail: LiveData<Resource<MovieDetail>> = _movieDetail

    fun setImdbId(imdbId: String) {
        _imdbId.value = imdbId
    }

    fun onBackPressed(){
        getNavigator()?.onBackPressed()
    }

    fun setNavigator(navigator: MovieDetailNavigator) {
        mainNavigator = WeakReference(navigator)
    }

    private fun getNavigator() = mainNavigator.get()

}