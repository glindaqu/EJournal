package ru.glindaqu.ejournal.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PairDao {
    @Query("SELECT title FROM Pair")
    fun getAllTitles(): Flow<List<String>>

    @Query("INSERT INTO Pair(title, teacherId) VALUES (:title, 1)")
    suspend fun insert(title: String)
}
