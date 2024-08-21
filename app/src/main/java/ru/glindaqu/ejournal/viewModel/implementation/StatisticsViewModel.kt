package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.Skip
import ru.glindaqu.ejournal.viewModel.api.IStatisticsViewModel

class StatisticsViewModel(
    application: Application,
) : AndroidViewModel(application),
    IStatisticsViewModel {
    private val studentsDao = AppDB.getDatabase(application).getPeopleDao()
    private val skipDao = AppDB.getDatabase(application).getSkipDao()
    private val markDao = AppDB.getDatabase(application).getMarkDao()
    private val subjectsDao = AppDB.getDatabase(application).getPairDao()

    fun getSubjects(): Flow<List<Pair>> = subjectsDao.getAllPairs()

    fun getStudents(): Flow<List<People>> = studentsDao.getAllStudents()

    fun getAllSkipsByRange(
        start: Long,
        end: Long,
    ): Flow<List<Skip>> = skipDao.getAllSkipsByRange(start, end)

    fun getAllMarksByRange(
        start: Long,
        end: Long,
    ): Flow<List<Mark>> = markDao.getAllMarksByRange(start, end)
}
