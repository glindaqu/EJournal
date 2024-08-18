package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.viewModel.api.IJournalViewModel
import java.util.Date

class JournalViewModel(
    app: Application,
) : AndroidViewModel(app),
    IJournalViewModel {
    private var pairDao = AppDB.getDatabase(app).getPairDao()
    private var studentsDao = AppDB.getDatabase(app).getPeopleDao()
    private var markDao = AppDB.getDatabase(app).getMarkDao()

    val pickedSubject = MutableStateFlow(Pair())
    val selectedDate = MutableStateFlow(Date().time)

    fun getAllStudents(): Flow<List<People>> = studentsDao.getAllStudents()

    fun getAllMarksBy(
        date: Long,
        studentId: Int,
        pairId: Int,
    ): Flow<List<Mark>> = markDao.getAllMarksBy(date, studentId, pairId)

    fun deleteMarkBy(mark: Mark) {
        viewModelScope.launch {
            markDao.deleteMark(mark.id!!)
        }
    }

    override fun getSubjects(): Flow<List<Pair>> = pairDao.getAllPairs()

    fun addMark(
        pairId: Int,
        date: Long,
        studentId: Int,
        mark: Int,
    ) {
        viewModelScope.launch {
            markDao.add(studentId = studentId, pairId = pairId, date = date, value = mark)
        }
    }
}
