package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.dataModels.AcademicPair
import ru.glindaqu.ejournal.dataModels.AcademicPairInfo
import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.dataModels.StudentDay
import ru.glindaqu.ejournal.dataModels.TeacherInfo
import ru.glindaqu.ejournal.database.room.AppDB
import ru.glindaqu.ejournal.viewModel.api.IJournalViewModel
import java.lang.ref.WeakReference

class JournalViewModel(
    app: Application,
) : AndroidViewModel(app),
    IJournalViewModel {
    val studentsList =
        listOf(
            JournalRowData(
                id = 1,
                studentName = "Дарья",
                studentLastname = "Воробьева",
                studentPatronymic = "Алексеевна",
                data =
                    arrayOf(
                        StudentDay(
                            date = 1707350400000,
                            pairs =
                                arrayOf(
                                    AcademicPair(
                                        uid = 1,
                                        pairInfo =
                                            AcademicPairInfo(
                                                id = 1,
                                                teacher =
                                                    TeacherInfo(
                                                        id = 1,
                                                        name = "Виктория",
                                                        lastname = "Васечко",
                                                        patronymic = "Николаевна",
                                                    ),
                                                title = "Математика",
                                            ),
                                        isStudentAbsence = false,
                                        marks = arrayOf(5, 5),
                                    ),
                                ),
                        ),
                        StudentDay(
                            date = 1706918400000,
                            pairs =
                                arrayOf(
                                    AcademicPair(
                                        uid = 1,
                                        pairInfo =
                                            AcademicPairInfo(
                                                id = 1,
                                                teacher =
                                                    TeacherInfo(
                                                        id = 1,
                                                        name = "Виктория",
                                                        lastname = "Васечко",
                                                        patronymic = "Николаевна",
                                                    ),
                                                title = "Математика",
                                            ),
                                        isStudentAbsence = false,
                                        marks = arrayOf(5),
                                    ),
                                ),
                        ),
                    ),
            ),
            JournalRowData(
                id = 2,
                studentName = "Анастасия",
                studentLastname = "Литвинцева",
                studentPatronymic = "Павловна",
                data =
                    arrayOf(
                        StudentDay(
                            date = 1707350400000,
                            pairs =
                                arrayOf(
                                    AcademicPair(
                                        uid = 1,
                                        pairInfo =
                                            AcademicPairInfo(
                                                id = 1,
                                                teacher =
                                                    TeacherInfo(
                                                        id = 1,
                                                        name = "Виктория",
                                                        lastname = "Васечко",
                                                        patronymic = "Николаевна",
                                                    ),
                                                title = "Математика",
                                            ),
                                        isStudentAbsence = false,
                                        marks = arrayOf(5, 5),
                                    ),
                                ),
                        ),
                        StudentDay(
                            date = 1706918400000,
                            pairs =
                                arrayOf(
                                    AcademicPair(
                                        uid = 1,
                                        pairInfo =
                                            AcademicPairInfo(
                                                id = 1,
                                                teacher =
                                                    TeacherInfo(
                                                        id = 1,
                                                        name = "Виктория",
                                                        lastname = "Васечко",
                                                        patronymic = "Николаевна",
                                                    ),
                                                title = "Математика",
                                            ),
                                        isStudentAbsence = false,
                                        marks = arrayOf(5),
                                    ),
                                ),
                        ),
                    ),
            ),
        )

    private lateinit var context: WeakReference<Context>

    override fun getSubjects(): Flow<List<String>> {
        if (this.context.get() == null) throw Exception("Context can't be null")
        val pairDao = AppDB.getDatabase(this.context.get()!!).getPairDao()
        return pairDao.getAllTitles()
    }

    override fun attachContext(context: Context) {
        this.context = WeakReference(context)
    }
}
