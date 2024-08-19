package ru.glindaqu.ejournal.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.tables.Skip

@Dao
interface SkipDao {
    @Query("SELECT * FROM Skip")
    fun getAllSkips(): Flow<List<Skip>>

    @Query(
        "SELECT * FROM Skip " +
            "WHERE date = :date AND studentId = :studentId AND pairId = :pairId",
    )
    fun getAllSkipsBy(
        date: Long,
        studentId: Int,
        pairId: Int,
    ): Flow<List<Skip>>

    @Query("DELETE FROM Skip WHERE uid = :id")
    suspend fun deleteSkip(id: Long)

    @Query(
        "INSERT INTO Skip(date, pairId, studentId) " +
            "VALUES (:date, :pairId, :studentId)",
    )
    suspend fun addSkip(
        date: Long,
        pairId: Int,
        studentId: Int,
    )
}
