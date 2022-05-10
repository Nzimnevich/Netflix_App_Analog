package ru.androidschool.intensiv.presentation.tvshows

import ru.androidschool.intensiv.domain.usecase.TVShowsUseCase
import ru.androidschool.intensiv.presentation.base.BasePresenter
import timber.log.Timber

class TVShowsPresenter(private val useCase: TVShowsUseCase) :
    BasePresenter<TVShowsPresenter.TVShowsView>() {

    fun getMovies() {
        useCase.getTvShows()
            .subscribe(
                { it ->
                    val tv = it.movies
                    val moviesList = tv?.map { TVItem(it) { movies -> } }?.toList()
                    if (moviesList != null) {
                        view?.showMovies(moviesList)
                    }
                },
                { t ->
                    Timber.e(t, t.toString())
                    view?.showEmptyMovies()
                }
            )
    }

    interface TVShowsView {
        fun showMovies(movies: List<TVItem>)
        fun showEmptyMovies()
    }
}
