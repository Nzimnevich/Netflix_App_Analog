package ru.androidschool.intensiv.data.mappers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieResponse
import ru.androidschool.intensiv.data.vo.MyMovie
import ru.androidschool.intensiv.presentation.feed.FeedFragment.Companion.KEY_ID
import ru.androidschool.intensiv.presentation.feed.MainCardContainer
import ru.androidschool.intensiv.presentation.feed.MovieItem

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
        bundle.putString(KEY_ID, movie.id.toString())
        fragment.findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

}
