package ru.glindaqu.ejournal.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.dataModels.JournalRowData
import ru.glindaqu.ejournal.modules.dayInfo.DayInfoDialog
import ru.glindaqu.ejournal.modules.dayInfo.DayInfoDialogState
import ru.glindaqu.ejournal.modules.dayInfo.rememberDayInfoDialogState
import ru.glindaqu.ejournal.modules.subjectPick.SubjectPick
import ru.glindaqu.ejournal.modules.subjectPick.rememberSubjectPickState
import ru.glindaqu.ejournal.modules.wheelDatePick.WheelDatePick
import ru.glindaqu.ejournal.modules.wheelDatePick.rememberWheelDatePickState
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx().toInt() }

suspend fun scrollHeaderToDay(
    day: Int,
    itemSizeInPx: Int,
    scrollState: ScrollState,
) {
    scrollState.animateScrollTo(day * itemSizeInPx)
}

@Suppress("ktlint:standard:function-naming")
@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@Composable
fun Journal() {
    val calendar = Calendar.getInstance()
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

    val displayOnlySurname =
        derivedStateOf {
            when {
                hScrollState.value <= 0 -> false
                else -> true
            }
        }

    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]
    viewModel.attachContext(LocalContext.current)

    val wheelDatePickState = rememberWheelDatePickState()
    val dayInfoDialogState = rememberDayInfoDialogState()
    val subjectPickState = rememberSubjectPickState()

    subjectPickState.subjectsList =
        viewModel.getSubjects().collectAsState(initial = listOf("")).value

    WheelDatePick(
        state = wheelDatePickState,
        defaultDate = selectedDate,
        onDateSelected = { selectedDate = it },
    )
    SubjectPick(state = subjectPickState)
    DayInfoDialog(state = dayInfoDialogState)

    Column {
        Row {
            Button(onClick = { wheelDatePickState.show = true }) {
                Text(text = SimpleDateFormat("d MMMM", Locale("ru")).format(selectedDate))
            }
            Button(onClick = { subjectPickState.show() }) {
                Text(text = "Предмет")
            }
        }
        Column {
            TableHeader(
                daysInMonth = daysInMonth,
                padding = studentListWidth,
                scrollState = hScrollState,
            )
            TableBody(
                daysCount = daysInMonth,
                data = viewModel.studentsList,
                displayOnlySurname = displayOnlySurname.value,
                scrollState = hScrollState,
                dialogState = dayInfoDialogState,
            ) {
                studentListWidth = with(density) { it.size.width.toDp() }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun TableHeader(
    daysInMonth: Int,
    padding: Dp,
    scrollState: ScrollState,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(start = padding)
                .horizontalScroll(scrollState),
    ) {
        for (i in 1..daysInMonth) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .padding(end = 1.dp)
                        .size(DEFAULT_TABLE_CELL_SIZE - 2.dp),
            ) {
                Text(
                    text = i.toString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun TableBody(
    daysCount: Int,
    data: List<JournalRowData>,
    displayOnlySurname: Boolean,
    scrollState: ScrollState,
    dialogState: DayInfoDialogState,
    reassignStudentsListWidth: (LayoutCoordinates) -> Unit,
) {
    Row(
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
    ) {
        StudentsNames(students = data, onGlobalPositioned = {
            reassignStudentsListWidth(it)
        }, item = {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .padding(top = 1.dp)
                        .height(DEFAULT_TABLE_CELL_SIZE - 2.dp)
                        .background(MaterialTheme.colorScheme.onBackground),
            ) {
                Text(
                    text =
                        if (displayOnlySurname) {
                            it.studentLastname
                        } else {
                            "${it.studentLastname} " + "${it.studentName} " + it.studentPatronymic
                        },
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )
            }
        })
        StudentStats(
            daysCount = daysCount,
            data = data,
            scrollState = scrollState,
            dialogState = dialogState,
        )
    }
}

@SuppressLint("SimpleDateFormat")
@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentStats(
    daysCount: Int,
    data: List<JournalRowData>,
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
                for (i in 1..daysCount) {
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
                                calendar.get(Calendar.MONTH),
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

@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentsStatsItem(
    text: String,
    date: Long,
    studentId: Int,
    click: (studentId: Int, date: Long) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .padding(top = 1.dp, end = 1.dp)
                .size(DEFAULT_TABLE_CELL_SIZE - 2.dp)
                .background(MaterialTheme.colorScheme.onBackground)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.background),
                ) {
                    click(studentId, date)
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.Black,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentsNames(
    students: List<JournalRowData>,
    onGlobalPositioned: (size: LayoutCoordinates) -> Unit,
    item: @Composable (JournalRowData) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .wrapContentSize()
                .onGloballyPositioned { coordinates ->
                    onGlobalPositioned(coordinates)
                },
    ) {
        for (i in students.indices) {
            item(students[i])
        }
    }
}
