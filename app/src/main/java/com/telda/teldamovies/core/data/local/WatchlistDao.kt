package com.telda.teldamovies.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    suspend fun getAll(): List<WatchlistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: WatchlistEntity)

    @Delete
    suspend fun delete(movie: WatchlistEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE movieId = :movieId)")
    suspend fun isInWatchlist(movieId: Int): Boolean
}