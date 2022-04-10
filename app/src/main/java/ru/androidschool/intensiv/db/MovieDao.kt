package ru.androidschool.intensiv.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movies")
    fun getMovies(): Observable<List<MovieEntity>>

    @Insert
    fun save(movies: List<MovieEntity>): Completable

    @Update
    fun update(movies: List<MovieEntity>): Completable

    @Delete
    fun delete(movie: MovieEntity): Completable
}
