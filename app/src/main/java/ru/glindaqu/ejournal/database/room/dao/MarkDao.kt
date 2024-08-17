package ru.glindaqu.ejournal.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.tables.Mark

@Dao
interface MarkDao {
    @Query("SELECT * FROM Mark WHERE date = :date AND studentId = :studentId AND pairId = :pairId")
    fun getAllMarks(
        date: Long,
        studentId: Int,
        pairId: Int,
    ): Flow<List<Mark>>

    @Query("DELETE FROM Mark WHERE date = :date AND studentId = :studentId AND pairId = :pairId")
    fun deleteMark(
        date: Long,
        studentId: Int,
        pairId: Int,
    )

    @Query(
        "INSERT INTO Mark(studentId, pairId, date, value) " +
            "VALUES (:studentId, :pairId, :date, :value)",
    )
    fun add(
        studentId: Int,
        pairId: Int,
        date: Long,
        value: Int,
    )
}
