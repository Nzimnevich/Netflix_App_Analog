package ru.androidschool.intensiv.domain.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.MovieResponse

interface MoviesRepository {
    fun getMovies(): Single<MovieResponse>
}
