package ru.glindaqu.ejournal.screens.journal.table

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.modules.dayInfo.DayInfoDialogState
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentStats(
    scrollState: ScrollState,
    dialogState: DayInfoDialogState,
) {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]
    val studentsList by viewModel.getAllStudents().collectAsState(initial = listOf())
    val subject by viewModel.pickedSubject.collectAsState()

    when {
        subject.id == null || studentsList.isEmpty() -> {}
        else ->
            StudentStatsBody(
                studentsList = studentsList,
                scrollState = scrollState,
                dialogState = dialogState,
            )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun StudentStatsBody(
    studentsList: List<People>,
    scrollState: ScrollState,
    dialogState: DayInfoDialogState,
) {
    val calendar = Calendar.getInstance()
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]
    val subject by viewModel.pickedSubject.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    Column {
        studentsList.forEach { student ->
            Row(
                modifier =
                    Modifier
                        .horizontalScroll(scrollState)
                        .background(MaterialTheme.colorScheme.background),
            ) {
                for (i in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    val date = Date(calendar.get(Calendar.YEAR), Date(selectedDate).month, i)
                    val marks by viewModel
                        .getAllMarksBy(date.time, student.id!!, subject.id!!)
                        .collectAsState(
                            initial = listOf(),
                        )
                    StudentsStatsItem(
                        marks = marks.filter { it.date == date.time },
                        date = date.time,
                        student = student,
                        dialogState = dialogState,
                    )
                }
            }
        }
    }
}
