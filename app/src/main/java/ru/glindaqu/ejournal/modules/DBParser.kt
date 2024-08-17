package ru.glindaqu.ejournal.modules

import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.database.room.tables.People

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
    }
}
