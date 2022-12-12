package com.burakkodaloglu.retrofit_movie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.burakkodaloglu.retrofit_movie.paging.MoviesPagingSource
import com.burakkodaloglu.retrofit_movie.repository.ApiRepository
import com.burakkodaloglu.retrofit_movie.response.MovieDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: ApiRepository) : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val moviesList = Pager(PagingConfig(1)) {
        MoviesPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    val detailsMovie = MutableLiveData<MovieDetailsResponse>()
    fun loadDetailsMovie(id:Int)=viewModelScope.launch {
        loading.postValue(true)
        val response=repository.getMovieDetails(id)
        if (response.isSuccessful){
            detailsMovie.postValue(response.body())
        }
        loading.postValue(false)
    }
}