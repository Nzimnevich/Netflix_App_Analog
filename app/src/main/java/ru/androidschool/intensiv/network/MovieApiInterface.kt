package ru.androidschool.intensiv.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.androidschool.intensiv.data.MovieResponse

interface MovieApiInterface {

    @GET("movie/now_playing")
    fun getAllMovies(@Query("api_key") apiKey: String, @Query("language") language: String): Call<MovieResponse>

    @GET("/movie/upcoming")
    fun getAllNovelties(): Call<List<MovieResponse>>

    @GET("/movie/popular")
    fun getPopularMovies(): Call<List<MovieResponse>>

    @GET("/tv/popular")
    fun getPopularTV(): Call<List<MovieResponse>>
}
