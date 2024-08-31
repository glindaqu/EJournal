package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.Skip
import ru.glindaqu.ejournal.viewModel.api.IDetailViewModel

class DetailViewModel(
    application: Application,
) : AndroidViewModel(application),
    IDetailViewModel {
    private val studentDao = AppDB.getDatabase(application).getPeopleDao()
    private val markDao = AppDB.getDatabase(application).getMarkDao()
    private val skipDao = AppDB.getDatabase(application).getSkipDao()
    private val pairDao = AppDB.getDatabase(application).getPairDao()

    fun getStudentById(id: Int): Flow<People> = studentDao.getStudentAsync(id)

    fun getAllMarksByStudent(id: Int): Flow<List<Mark>> = markDao.getAllByMarksStudent(id)

    fun getAllSubjects(): Flow<List<Pair>> = pairDao.getAllPairs()

    fun getAllSkipsByStudent(
        id: Int,
        start: Long,
        end: Long,
    ): Flow<List<Skip>> = skipDao.getAllSkipsByStudentInRange(id, start, end)

    fun getAllSkipsByAllTime(studentId: Int): Flow<List<Skip>> = skipDao.getAllSkipsByStudentInAllTime(studentId)
}
