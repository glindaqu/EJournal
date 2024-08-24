package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.viewModel.api.ISettingsViewModel

class SettingsViewModel(
    application: Application,
) : AndroidViewModel(application),
    ISettingsViewModel {
    private val skipDao = AppDB.getDatabase(application).getSkipDao()
    private val markDao = AppDB.getDatabase(application).getMarkDao()

    fun deleteAllMarks() {
        viewModelScope.launch {
            markDao.deleteAll()
        }
    }

    fun deleteAllSkips() {
        viewModelScope.launch {
            skipDao.deleteAll()
        }
    }
}
