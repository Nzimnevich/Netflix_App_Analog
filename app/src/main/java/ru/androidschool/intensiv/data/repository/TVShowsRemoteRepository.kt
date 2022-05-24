package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.domain.repository.MoviesRepository
import ru.androidschool.intensiv.network.MovieApiClient

class TVShowsRemoteRepository(val apiClient: MovieApiClient) : MoviesRepository {
    override fun getMovies(): Single<MovieResponse> {
        return apiClient.apiClient.getPopularTV()
    }
}
