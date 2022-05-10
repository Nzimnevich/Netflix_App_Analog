package ru.androidschool.intensiv

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import ru.androidschool.intensiv.data.network.MovieApiClient

class MovieResponseTest {
    var ss: String? = null
    @Test
    @DisplayName("The answer is not empty")
    fun test() {
        val allRecipes = MovieApiClient.apiClient.getAllMovies()
        Assert.assertNotNull(allRecipes)
    }
}
