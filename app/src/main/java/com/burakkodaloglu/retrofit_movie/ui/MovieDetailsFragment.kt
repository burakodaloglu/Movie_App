package com.burakkodaloglu.retrofit_movie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.burakkodaloglu.retrofit_movie.R
import com.burakkodaloglu.retrofit_movie.databinding.FragmentMovieDetailsBinding
import com.burakkodaloglu.retrofit_movie.utils.POSTER_BASE_URL
import com.burakkodaloglu.retrofit_movie.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private var movieId = 0
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MoviesViewModel by viewModels()

    val TAG = "MovieDetailsFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.movieId
        if (movieId > 0) {
            viewModel.loadDetailsMovie(movieId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            viewModel.detailsMovie.observe(viewLifecycleOwner) { response ->
                val moviePosterURL = POSTER_BASE_URL + response.posterPath
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                imgMovieBack.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }

                tvMovieTitle.text = response.title
                tvMovieTagLine.text = response.tagline
                tvMovieDateRelease.text = response.releaseDate
                tvMovieRating.text = response.voteAverage.toString()
                tvMovieRuntime.text = response.runtime.toString()
                tvMovieBudget.text = response.budget.toString()
                tvMovieRevenue.text = response.revenue.toString()
                tvMovieOverview.text = response.overview
            }

            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    prgBarMovies.visibility = View.VISIBLE
                } else {
                    prgBarMovies.visibility = View.INVISIBLE
                }
            }
        }
    }

}