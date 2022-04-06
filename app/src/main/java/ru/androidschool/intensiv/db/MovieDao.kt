package ru.androidschool.intensiv.db

import androidx.room.*
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.data.MyMovie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movies")
    fun getMovies()

    @Insert
    fun save(movies: List<MyMovie>)

    @Update
    fun update(movies: List<MyMovie>)

    @Delete
    fun delete(movie: Movie)
}
