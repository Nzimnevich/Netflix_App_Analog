package ru.androidschool.intensiv.domain.usecase

import io.reactivex.Single
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.data.repository.TVShowsRemoteRepository
import ru.androidschool.intensiv.extensions.applySchedulers

class TVShowsUseCase(private val repository: TVShowsRemoteRepository) {

    fun getTvShows(): Single<MovieResponse> {
        return repository.getMovies()
            .applySchedulers()
    }
}
