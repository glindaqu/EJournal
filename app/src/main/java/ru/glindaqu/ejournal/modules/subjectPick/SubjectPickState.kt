package ru.glindaqu.ejournal.modules.subjectPick

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.glindaqu.ejournal.database.room.tables.Pair

class SubjectPickState internal constructor() {
    private var show by mutableStateOf(false)
    var subject by mutableStateOf(Pair())
    var subjectsList by mutableStateOf(listOf(Pair()))

    fun show() {
        this.show = true
    }

    fun close() {
        this.show = false
    }

    fun isOpen(): Boolean = this.show
}
