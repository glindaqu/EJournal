package ru.glindaqu.ejournal.screens.journal.table

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.modules.dayInfo.DayInfoDialogState
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentStats(
    data: List<JournalRowData>,
    month: Int,
    scrollState: ScrollState,
    dialogState: DayInfoDialogState,
) {
    val calendar = Calendar.getInstance()
    Column {
        data.forEach { student ->
            Row(
                modifier =
                    Modifier
                        .horizontalScroll(scrollState)
                        .background(MaterialTheme.colorScheme.background),
            ) {
                for (i in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    val daysArr = student.data.filter { sd -> Date(sd.date).date == i }
                    val text =
                        if (daysArr.isEmpty()) {
                            ""
                        } else {
                            daysArr[0].pairs.joinToString { academicPair ->
                                academicPair.marks.joinToString { it.toString() }
                            }
                        }
                    StudentsStatsItem(
                        text = text,
                        date =
                            Date(
                                calendar.get(Calendar.YEAR),
                                month,
                                i,
                            ).time,
                        studentId = student.id,
                    ) { _, date ->
                        dialogState.markList.clear()
                        if (daysArr.isNotEmpty()) {
                            daysArr[0].pairs.map {
                                for (j in it.marks) dialogState.markList.add(j.toInt())
                            }
                        }
                        dialogState.show(
                            name = student.studentLastname + " " + student.studentName,
                            date = date,
                        )
                    }
                }
            }
        }
    }
}
