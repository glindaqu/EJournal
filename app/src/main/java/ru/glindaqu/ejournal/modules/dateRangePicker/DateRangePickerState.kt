package ru.glindaqu.ejournal.modules.dateRangePicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DateRangePickerState internal constructor() {
    private var show by mutableStateOf(false)

    fun show() {
        this.show = true
    }

    fun close() {
        this.show = false
    }

    fun showing(): Boolean = this.show
}
