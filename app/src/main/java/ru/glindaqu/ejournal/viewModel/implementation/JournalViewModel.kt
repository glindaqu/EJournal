package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.glindaqu.ejournal.dataModels.AcademicPair
import ru.glindaqu.ejournal.dataModels.AcademicPairInfo
import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.dataModels.StudentDay
import ru.glindaqu.ejournal.dataModels.TeacherInfo
import ru.glindaqu.ejournal.viewModel.api.IJournalViewModel

class JournalViewModel(
    app: Application,
) : AndroidViewModel(app),
    IJournalViewModel {
    override val studentsList =
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
}
