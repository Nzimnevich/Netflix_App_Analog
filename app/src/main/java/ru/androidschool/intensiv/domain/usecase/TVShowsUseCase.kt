package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.domain.repository.MoviesRepository
import ru.androidschool.intensiv.extensions.applySchedulers

class TVShowsUseCase(private val repository: MoviesRepository) {

    fun getTvShows(): Single<MovieResponse> {
        return repository.getMovies()
            .applySchedulers()
    }
}
