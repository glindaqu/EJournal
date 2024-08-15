package ru.glindaqu.ejournal.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.tables.People

@Dao
interface PeopleDao {
    @Query("SELECT * FROM People")
    fun getAll(): Flow<List<People>>

    @Query("SELECT * FROM People WHERE role = 'student'")
    fun getAllStudents(): Flow<List<People>>

    @Query("SELECT * FROM People WHERE role = 'teacher'")
    fun getAllTeachers(): Flow<List<People>>

    @Query(
        "UPDATE People SET name = :name, lastname = :surname, " +
            "patronymic = :patronymic WHERE id = :id",
    )
    suspend fun update(
        id: Int,
        name: String,
        surname: String,
        patronymic: String,
    )

    @Query("DELETE FROM People WHERE id = :id")
    suspend fun delete(id: Int)

    @Query(
        "INSERT INTO People(name, lastname, patronymic, role) VALUES " +
            "(:name, :lastname, :patronymic, :role)",
    )
    suspend fun insert(
        name: String,
        lastname: String,
        patronymic: String,
        role: String,
    )

    @Query("SELECT * FROM People WHERE id = :id LIMIT 1")
    suspend fun get(id: Int): People
}
