package ru.androidschool.intensiv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MyMovie
import ru.androidschool.intensiv.ui.feed.FeedFragment
import ru.androidschool.intensiv.ui.feed.MainCardContainer
import ru.androidschool.intensiv.ui.feed.MovieItem

object MovieMapper {

    fun getMovieForUI(
        movies: List<MyMovie>,
        intResourses: Int,
        options: NavOptions,
        fragment: Fragment
    ): List<MainCardContainer> {
        val items: List<MainCardContainer> = listOf(
            MainCardContainer(
                intResourses,
                movies.map {
                    MovieItem(it) { movie ->
                        openMovieDetails(
                            movie, options, fragment
                        )
                    }
                }.toList()
            )
        )
        return items
    }

    private fun openMovieDetails(movie: MyMovie, options: NavOptions, fragment: Fragment) {
        val bundle = Bundle()
        bundle.putString(FeedFragment.KEY_TITLE, movie.title)
        fragment.findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }
}
