package ru.glindaqu.ejournal.modules.dayInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DayInfoDialogState internal constructor() {
    var show by mutableStateOf(false)
    var isAbsence by mutableStateOf(false)

    var studentName by mutableStateOf("")
    var studentId by mutableIntStateOf(0)
    var date by mutableLongStateOf(0L)

    fun show(
        name: String = this.studentName,
        date: Long = this.date,
        studentId: Int,
    ) {
        this.studentName = name
        this.date = date
        this.show = true
        this.studentId = studentId
    }
}
