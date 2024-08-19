@file:Suppress("ktlint:standard:filename")

package ru.glindaqu.ejournal.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.tables.Pair

@Dao
interface PairDao {
    @Query("SELECT title FROM Pair")
    fun getAllTitles(): Flow<List<String>>

    @Query("SELECT * FROM Pair")
    fun getAllPairs(): Flow<List<Pair>>

    @Query("INSERT INTO Pair(title, teacherId) VALUES (:title, 1)")
    suspend fun insert(title: String)

    @Query("DELETE FROM Pair WHERE title = :title")
    suspend fun delete(title: String)

    @Query("UPDATE Pair SET title = :newTitle WHERE title = :oldTitle")
    suspend fun update(
        newTitle: String,
        oldTitle: String,
    )
}
