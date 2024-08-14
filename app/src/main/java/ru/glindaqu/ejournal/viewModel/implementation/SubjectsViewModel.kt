package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.screens.subjects.SubjectsUIState
import ru.glindaqu.ejournal.viewModel.api.ISubjectsViewModel

class SubjectsViewModel(
    app: Application,
) : AndroidViewModel(app),
    ISubjectsViewModel {
    val uiState = MutableStateFlow(SubjectsUIState.VIEW)
    private val database: AppDB

    init {
        database = AppDB.getDatabase(app)
    }

    fun insertSubject(title: String) {
        viewModelScope.launch {
            database.getPairDao().insert(title)
        }
    }

    override fun getAllSubject(): Flow<List<String>> = database.getPairDao().getAllTitles()

    override fun deleteSubject(title: String) {
        viewModelScope.launch {
            database.getPairDao().delete(title)
        }
    }

    override fun update(
        old: String,
        new: String,
    ) {
        viewModelScope.launch {
            database.getPairDao().update(new, old)
        }
    }
}
