package ru.androidschool.intensiv

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import ru.androidschool.intensiv.BuildConfig.THE_MOVIE_DATABASE_API
import ru.androidschool.intensiv.network.MovieApiClient

class MovieResponseTest {

    @Test
    @DisplayName("The answer is not empty")
    fun test() {
        val allRecipes = MovieApiClient.apiClient.getAllMovies(THE_MOVIE_DATABASE_API, "ru")
        Assert.assertNotNull(allRecipes)
    }
}
