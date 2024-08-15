package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.database.room.dao.PeopleDao
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.screens.students.StudentsUIState
import ru.glindaqu.ejournal.viewModel.api.IStudentsViewModel

class StudentsViewModel(
    application: Application,
) : AndroidViewModel(application),
    IStudentsViewModel {
    val uiState = MutableStateFlow(StudentsUIState.VIEW)
    private val dao: PeopleDao

    init {
        dao = AppDB.getDatabase(application).getPeopleDao()
    }

    override fun insert(
        name: String,
        lastname: String,
        patronymic: String,
    ) {
        viewModelScope.launch {
            dao.insert(name, lastname, patronymic, "student")
        }
    }

    override fun getStudents(): Flow<List<People>> = dao.getAllStudents()

    override suspend fun getStudent(id: Int): People = dao.get(id)

    override fun delete(id: Int) {
        viewModelScope.launch {
            dao.delete(id)
        }
    }

    override fun update(
        id: Int,
        name: String,
        lastname: String,
        patronymic: String,
    ) {
        viewModelScope.launch {
            dao.update(id, name, lastname, patronymic)
        }
    }
}
