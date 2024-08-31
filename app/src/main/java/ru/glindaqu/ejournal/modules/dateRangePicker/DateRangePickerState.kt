package ru.glindaqu.ejournal.modules.dateRangePicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Calendar
import java.util.Date

class DateRangePickerState internal constructor() {
    private var show by mutableStateOf(false)

    private val calendar: Calendar = Calendar.getInstance()

    var defaultStartDate =
        Date(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.getActualMinimum(Calendar.DAY_OF_MONTH),
        )

    var defaultEndDate =
        Date(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
        )

    fun show() {
        this.show = true
    }

    fun close() {
        this.show = false
    }

    fun setDefaults(
        start: Date,
        end: Date,
    ) {
        defaultStartDate = start
        defaultEndDate = end
    }

    fun showing(): Boolean = this.show
}
