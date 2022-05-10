package ru.androidschool.intensiv.data.repository

import io.reactivex.Single
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.domain.repository.MoviesRepository

class TVShowsRemoteRepository : MoviesRepository {
    override fun getMovies(): Single<MovieResponse> {
        return MovieApiClient.apiClient.getPopularTV()
    }
}
