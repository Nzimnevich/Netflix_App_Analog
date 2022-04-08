package ru.androidschool.intensiv.db

import androidx.room.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movies")
    fun getMovies(): List<MovieEntity>

    @Insert
    fun save(movies: List<MovieEntity>)

    @Update
    fun update(movies: List<MovieEntity>)

    @Delete
    fun delete(movie: MovieEntity)
}
