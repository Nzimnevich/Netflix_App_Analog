package ru.androidschool.intensiv.data.mappers

import ru.androidschool.intensiv.data.dto.MyMovie
import ru.androidschool.intensiv.presentation.feed.MainCardContainer
import ru.androidschool.intensiv.presentation.feed.MovieItem

object MovieMapper {

    fun getMovieForUI(
        movies: List<MyMovie>,
        intResourses: Int,
        onClick: (movie: MyMovie) -> Unit
    ): List<MainCardContainer> {
        val items: List<MainCardContainer> = listOf(
            MainCardContainer(
                intResourses,
                movies.map {
                    MovieItem(it) { movie ->
                        onClick.invoke(it)
                    }
                }.toList()
            )
        )
        return items
    }
}
