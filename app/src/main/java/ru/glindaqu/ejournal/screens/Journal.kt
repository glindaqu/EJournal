package ru.glindaqu.ejournal.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.modules.wheelDatePick.WheelDatePick
import ru.glindaqu.ejournal.modules.wheelDatePick.rememberWheelDatePickState
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx().toInt() }

suspend fun scrollHeaderToDay(day: Int, itemSizeInPx: Int, scrollState: ScrollState) {
    scrollState.animateScrollTo(day * itemSizeInPx)
}

@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@Composable
fun Journal() {

    val calendar: Calendar = Calendar.getInstance()
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    val density = LocalDensity.current
    var studentListWidth by remember { mutableStateOf(0.dp) }
    val hScrollState = rememberScrollState()
    val offsetPx = DEFAULT_TABLE_CELL_SIZE.toPx()

    var selectedDate by remember {
        mutableStateOf(Date())
    }

    LaunchedEffect(Unit) {
        scrollHeaderToDay(LocalDate.now().dayOfMonth - 1, offsetPx, hScrollState)
    }

    val displayOnlySurname = derivedStateOf {
        when {
            hScrollState.value <= 0 -> false
            else -> true
        }
    }

    val wheelDatePickState = rememberWheelDatePickState()

    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]

    WheelDatePick(state = wheelDatePickState, defaultDate = selectedDate, onDateSelected = { selectedDate = it })

    Column {
        Row {
            Button(onClick = { wheelDatePickState.show = true }) {
                Text(text = SimpleDateFormat("dd MMMM").format(selectedDate))
            }
        }
        Column {
            TableHeader(
                daysInMonth = daysInMonth, padding = studentListWidth, scrollState = hScrollState
            )
            TableBody(
                daysCount = daysInMonth,
                data = viewModel.studentsList,
                displayOnlySurname = displayOnlySurname.value,
                scrollState = hScrollState
            ) {
                studentListWidth = with(density) { it.size.width.toDp() }
            }
        }
    }
}

@Composable
fun TableHeader(daysInMonth: Int, padding: Dp, scrollState: ScrollState) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(start = padding)
            .horizontalScroll(scrollState)
    ) {
        for (i in 1..daysInMonth) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(DEFAULT_TABLE_CELL_SIZE),
            ) {
                Text(
                    text = i.toString(), color = Color.Black, textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TableBody(
    daysCount: Int,
    data: List<JournalRowData>,
    displayOnlySurname: Boolean,
    scrollState: ScrollState,
    reassignStudentsListWidth: (LayoutCoordinates) -> Unit
) {

    Row {
        StudentsNames(students = data, onGlobalPositioned = {
            reassignStudentsListWidth(it)
        }, item = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {
                Text(
                    text = if (displayOnlySurname) it.studentLastname else "${it.studentLastname} " + "${it.studentName} " + it.studentPatronymic,
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    modifier = Modifier.padding(5.dp)
                )
            }
        })
        StudentStats(daysCount = daysCount, data = data, scrollState = scrollState)
    }
}

@Composable
fun StudentStats(daysCount: Int, data: List<JournalRowData>, scrollState: ScrollState) {
    Column {
        data.forEach { student ->
            Row(modifier = Modifier.horizontalScroll(scrollState)) {
                for (i in 1..<daysCount) {
                    val rightDays = student.data.filter { sd ->
                        Date(sd.date).date == i
                    }
                    if (rightDays.isNotEmpty()) {
                        StudentsStatsItem(text = rightDays[0].pairs.joinToString { academicPair ->
                            academicPair.marks.joinToString {
                                it.toString()
                            }
                        })
                    } else {
                        StudentsStatsItem(text = "")
                    }
                }
            }
        }
    }
}

@Composable
fun StudentsStatsItem(text: String) {
    Box(modifier = Modifier.size(DEFAULT_TABLE_CELL_SIZE), contentAlignment = Alignment.Center) {
        Text(
            text = text, textAlign = TextAlign.Center, color = Color.Black
        )
    }
}

@Composable
fun StudentsNames(
    students: List<JournalRowData>,
    onGlobalPositioned: (size: LayoutCoordinates) -> Unit,
    item: @Composable (JournalRowData) -> Unit
) {
    Column(modifier = Modifier
        .background(Color.Black)
        .wrapContentSize()
        .onGloballyPositioned { coordinates ->
            onGlobalPositioned(coordinates)
        }) {
        for (i in students.indices) {
            item(students[i])
        }
    }
}
