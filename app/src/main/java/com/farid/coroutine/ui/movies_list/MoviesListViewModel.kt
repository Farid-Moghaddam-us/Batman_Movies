package com.farid.coroutine.ui.movies_list

import androidx.lifecycle.ViewModel
import com.farid.coroutine.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    lateinit var mainNavigator: WeakReference<MoviesListNavigator>

    val moviesList = repository.getMoviesList()

    var moviesListAdapter = MoviesListAdapter(object : MovieItemListener {
        override fun onClickMovie(imdbId: String) {
            getNavigator()?.openMovieDetail(imdbId)
        }
    })

    fun setNavigator(navigator: MoviesListNavigator) {
        mainNavigator = WeakReference(navigator)
    }

    fun getNavigator() = mainNavigator.get()

}