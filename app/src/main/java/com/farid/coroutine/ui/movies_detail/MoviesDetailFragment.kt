package com.farid.coroutine.ui.movies_detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.farid.coroutine.R
import com.farid.coroutine.databinding.MoviesDetailFragmentBinding
import com.farid.coroutine.ui.main.MainActivity
import com.farid.coroutine.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesDetailFragment : Fragment(), MovieDetailNavigator {

    companion object {
        fun newInstance(imdbId: String): MoviesDetailFragment {
            val args = Bundle()
            val fragment = MoviesDetailFragment()
            args.putString("imdbId", imdbId)
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel by viewModels<MoviesDetailViewModel>()
    private lateinit var binding: MoviesDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        if (arguments != null && requireArguments().containsKey("imdbId"))
            if (requireArguments().getString("imdbId")!!.isNotEmpty())
                requireArguments().getString("imdbId")?.let { viewModel.setImdbId(it) }
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        viewModel.movieDetail.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    binding.model = response.data
                    binding.posterImg.load(response.data?.poster)
                    if (response.data?.type == "movie")
                        binding.type.setCardBackgroundColor(resources.getColor(R.color.movies_card_color))
                    else binding.type.setCardBackgroundColor(resources.getColor(R.color.series_card_color))
                }
                Resource.Status.ERROR -> (requireActivity() as MainActivity).showMessage(
                    response.message ?: "network error"
                )
                Resource.Status.LOADING -> {}
            }
        }
    }

    override fun onBackPressed() {
        requireActivity().onBackPressed()
    }

}