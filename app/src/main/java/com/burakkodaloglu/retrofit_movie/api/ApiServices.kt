package com.burakkodaloglu.retrofit_movie.api

import com.burakkodaloglu.retrofit_movie.response.MovieDetailsResponse
import com.burakkodaloglu.retrofit_movie.response.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    //    https://api.themoviedb.org/3/movie/550?api_key=***
    //    https://api.themoviedb.org/3/movie/popular?api_key=***
    //    https://api.themoviedb.org/3/

    @GET("movie/popular")
    suspend fun getPopularMoviesList(@Query("page") page: Int): Response<MoviesListResponse>

    @GET("movie/{movie_id}")
    suspend

    fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetailsResponse>

}