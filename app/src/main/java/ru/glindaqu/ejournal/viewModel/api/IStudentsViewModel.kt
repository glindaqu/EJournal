package ru.glindaqu.ejournal.viewModel.api

import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.tables.People

interface IStudentsViewModel {
    fun insert(
        name: String,
        lastname: String,
        patronymic: String,
    )

    fun getStudents(): Flow<List<People>>

    suspend fun getStudent(id: Int): People

    fun delete(id: Int)

    fun update(
        id: Int,
        name: String,
        lastname: String,
        patronymic: String,
    )
}
