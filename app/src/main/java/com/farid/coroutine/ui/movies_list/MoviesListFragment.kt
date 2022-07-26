package com.farid.coroutine.ui.movies_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.farid.coroutine.R
import com.farid.coroutine.utils.Resource
import com.farid.coroutine.databinding.MoviesListFragmentBinding
import com.farid.coroutine.ui.main.MainActivity
import com.farid.coroutine.ui.movies_detail.MoviesDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : Fragment(), MoviesListNavigator {

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    private val viewModel by viewModels<MoviesListViewModel>()
    private lateinit var binding: MoviesListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        fetchMoviesList()
    }

    private fun fetchMoviesList() {
        viewModel.moviesList.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    binding.moviesListPb.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("TAG", "fetchMoviesList: ${it.size}")
                        viewModel.moviesListAdapter.setItems(response.data)
                        binding.moviesListRv.adapter = viewModel.moviesListAdapter
                    }
                }
                Resource.Status.ERROR -> {
                    binding.moviesListPb.visibility = View.INVISIBLE
                    (requireActivity() as MainActivity).showMessage(
                        response.message ?: "network error"
                    )
                }
                Resource.Status.LOADING -> binding.moviesListPb.visibility = View.VISIBLE
            }
        }
    }

    override fun openMovieDetail(imdbId: String) {
        (requireActivity() as MainActivity).changeFragment(
            R.id.fl_main,
            MoviesDetailFragment.newInstance(imdbId),
            "openMovieList"
        )
    }
}