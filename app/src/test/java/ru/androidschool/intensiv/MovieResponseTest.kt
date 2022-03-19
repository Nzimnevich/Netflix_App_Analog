package ru.androidschool.intensiv

import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.data.CastResponse
import ru.androidschool.intensiv.network.MovieApiClient

class MovieResponseTest {
    var ss: String? = null
    @Test
    @DisplayName("The answer is not empty")
    fun test() {
        val allRecipes = MovieApiClient.apiClient.getAllMovies()
        Assert.assertNotNull(allRecipes)
    }

    @Test
    @DisplayName("The answer is not empty")
    fun test1() {
        val allActors = MovieApiClient.apiClient.getMoviesCrewDetails()

        allActors.enqueue(object : Callback<CastResponse> {

            override fun onFailure(call: Call<CastResponse>, error: Throwable) {
            }

            override fun onResponse(
                call: Call<CastResponse>,
                response: Response<CastResponse>
            ) {

                val actors = response.body()?.cast
                print("!!!!!!!!!")
                print("!!!!!!!!!")
                print("!!!!!!!!!")
                print("!!!!!!!!!")
                print("!!!!!!!!!")
                print("!!!!!!!!!")
                print("!!!!!!!!!")
                print("!!!!!!!!!")
                ss = actors?.get(1)?.image
                print(ss)
            }
        }
        )
    }
}
