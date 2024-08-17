package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.database.room.dao.MarkDao
import ru.glindaqu.ejournal.database.room.dao.PairDao
import ru.glindaqu.ejournal.database.room.dao.PeopleDao
import ru.glindaqu.ejournal.modules.DBParser
import ru.glindaqu.ejournal.viewModel.api.IJournalViewModel
import java.lang.ref.WeakReference

class JournalViewModel(
    app: Application,
) : AndroidViewModel(app),
    IJournalViewModel {
    val studentsList = MutableStateFlow(mutableListOf<JournalRowData>())
    private lateinit var pairDao: PairDao
    private lateinit var studentsDao: PeopleDao
    private lateinit var markDao: MarkDao
    private lateinit var context: WeakReference<Context>

    override fun getSubjects(): Flow<List<String>> {
        if (this.context.get() == null) throw Exception("Context can't be null")
        return pairDao.getAllTitles()
    }

    fun addMark(
        pairId: Int,
        date: Long,
        studentId: Int,
        mark: Int,
    ) {
        markDao.add(studentId = studentId, pairId = pairId, date = date, value = mark)
    }

    override fun attachContext(context: Context) {
        this.context = WeakReference(context)
        init()
    }

    private fun init() {
        pairDao = AppDB.getDatabase(this.context.get()!!).getPairDao()
        studentsDao = AppDB.getDatabase(this.context.get()!!).getPeopleDao()
        markDao = AppDB.getDatabase(this.context.get()!!).getMarkDao()
        viewModelScope.launch {
            val students = studentsDao.getAllStudentsAsync()
            studentsList.value = mutableListOf()
            students.forEach {
                studentsList.value.add(DBParser.makeRow(it))
            }
        }
    }
}
