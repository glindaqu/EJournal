package ru.glindaqu.ejournal.modules

import ru.glindaqu.ejournal.dataModels.AcademicPair
import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.dataModels.StudentDay
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.People
import java.util.UUID

class DBParser {
    companion object {
        fun makeRow(student: People): JournalRowData {
            assert(student.id != null)
            assert(student.name != null)
            assert(student.lastname != null)
            assert(student.patronymic != null)
            return JournalRowData(
                id = student.id!!,
                studentName = student.name!!,
                studentLastname = student.lastname!!,
                studentPatronymic = student.patronymic!!,
                data = mutableListOf(),
            )
        }

        fun addMarks(
            rowData: JournalRowData,
            marks: List<Mark>,
        ) {
            marks.filter { mark -> mark.studentId == rowData.id }.forEach { item ->
                val need = rowData.data.find { day -> day.date == item.date }
                val pair =
                    AcademicPair(
                        uid = UUID.randomUUID(),
                        marks = mutableListOf(item.value),
                        isStudentAbsence = false,
                    )
                if (need != null) {
                    rowData.data[rowData.data.indexOf(need)].pairs.add(pair)
                } else {
                    rowData.data.add(StudentDay(item.date, mutableListOf(pair)))
                }
            }
        }
    }
}
