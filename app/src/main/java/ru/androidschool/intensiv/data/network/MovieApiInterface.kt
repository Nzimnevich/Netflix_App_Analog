package ru.androidschool.intensiv.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.data.CastDetailsResponse
import ru.androidschool.intensiv.data.MovieDetailsResponse
import ru.androidschool.intensiv.data.MovieResponse

interface MovieApiInterface {

    /**
     * return список фильмов
     */
    @GET("movie/now_playing")
    fun getAllMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MovieResponse>

    /**
     *  return список новых фильмов
     */
    @GET("movie/upcoming")
    fun getAllNovelties(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MovieResponse>

    /**
     *  return список популярных фильмов
     */
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MovieResponse>

    /**
     * return список популярных сериалов
     */
    @GET("tv/popular")
    fun getPopularTV(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MovieResponse>

    /**
     *  return информацию о фильме
     */
    @GET("movie/{movie_id}")
    fun getMoviesDetails(
        @Path("movie_id") movie_id: Int = 111,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<MovieDetailsResponse>

    /**
     * return информацию об актерском составе
     */
    @GET("movie/{movie_id}/credits")
    fun getMoviesCrewDetails(
        @Path("movie_id") movie_id: Int = 111,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru"
    ): Single<CastDetailsResponse>

    /**
     * return информацию об фильме, который ищут
     */
    @GET("search/movie")
    fun getSearchMovies(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DATABASE_API,
        @Query("language") language: String = "ru",
        @Query("query") query: String
    ): Single<MovieResponse>
}
