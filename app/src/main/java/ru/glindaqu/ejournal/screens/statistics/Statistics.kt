package ru.glindaqu.ejournal.screens.statistics

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.PeopleKReturnTypes
import ru.glindaqu.ejournal.database.room.tables.Skip
import ru.glindaqu.ejournal.modules.dateRangePicker.DateRangePicker
import ru.glindaqu.ejournal.modules.dateRangePicker.rememberDateRangePickerState
import ru.glindaqu.ejournal.screens.journal.table.Loading
import ru.glindaqu.ejournal.viewModel.implementation.StatisticsViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class StatUIState {
    LOADING,
    READY,
}

fun Modifier.rotateVertically(clockwise: Boolean = true): Modifier {
    val rotate = rotate(if (clockwise) 90f else -90f)

    val adjustBounds =
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            layout(placeable.height, placeable.width) {
                placeable.place(
                    x = -(placeable.width / 2 - placeable.height / 2),
                    y = -(placeable.height / 2 - placeable.width / 2),
                )
            }
        }
    return rotate then adjustBounds
}

@SuppressLint("UnrememberedMutableState")
@Suppress("ktlint:standard:function-naming")
@Composable
fun Statistics() {
    var dateStart by remember {
        mutableStateOf(
            Date(),
        )
    }
    var dateEnd by remember {
        mutableStateOf(
            Date(),
        )
    }

    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[StatisticsViewModel::class.java]
    val students by viewModel.getStudents().collectAsState(initial = listOf())
    val subjects by viewModel.getSubjects().collectAsState(initial = listOf())
    val marks by viewModel
        .getAllMarksByRange(dateStart.time, dateEnd.time)
        .collectAsState(initial = listOf())
    val skips by viewModel
        .getAllSkipsByRange(dateStart.time, dateEnd.time)
        .collectAsState(initial = listOf())

    val uiState by derivedStateOf {
        when {
            students.isEmpty() -> StatUIState.LOADING
            else -> StatUIState.READY
        }
    }

    when (uiState) {
        StatUIState.LOADING -> Loading()
        else ->
            Body(
                subjects = subjects,
                students = students,
                marks = marks,
                skips = skips,
                onDateSelected = { s, e ->
                    dateEnd = e
                    dateStart = s
                },
            )
    }
}

@SuppressLint("SimpleDateFormat", "DefaultLocale")
@Suppress("ktlint:standard:function-naming")
@Composable
private fun Body(
    subjects: List<Pair>,
    students: List<People>,
    marks: List<Mark>,
    skips: List<Skip>,
    onDateSelected: (Date, Date) -> Unit,
) {
    var legendSize by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val dateRangePickerState = rememberDateRangePickerState()

    var labelText by remember { mutableStateOf(SimpleDateFormat("dd MMMM", Locale("ru")).format(Date())) }

    DateRangePicker(state = dateRangePickerState, onDateSelected = { s, e ->
        onDateSelected(s, e)
        labelText =
            "${SimpleDateFormat("dd MMMM", Locale("ru")).format(s)} - ${SimpleDateFormat("dd MMMM", Locale("ru")).format(e)}"
    })

    Column {
        Row(
            modifier =
                Modifier
                    .padding(
                        horizontal = DEFAULT_HORIZONTAL_PADDING / 2,
                        vertical = DEFAULT_VERTICAL_PADDING / 2,
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Button(
                onClick = { dateRangePickerState.show() },
                shape =
                    RoundedCornerShape(
                        DEFAULT_CORNER_CLIP,
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier =
                    Modifier.shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                        clip = true,
                        ambientColor = Color.Black,
                        spotColor = Color.Black,
                    ),
            ) {
                Text(
                    text = labelText,
                    fontSize = 18.sp,
                )
            }
        }
        Column(
            modifier =
                Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = DEFAULT_CORNER_CLIP,
                            topEnd = DEFAULT_CORNER_CLIP,
                        ),
                    ).horizontalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.onBackground),
        ) {
            StatisticTableHeader(subjects = subjects, startPadding = legendSize)
            Row {
                LazyColumn(
                    modifier =
                        Modifier
                            .onGloballyPositioned {
                                legendSize = with(density) { it.size.width.toDp() }
                            }.background(MaterialTheme.colorScheme.onBackground),
                    contentPadding = PaddingValues(1.dp),
                ) {
                    items(students) {
                        StatisticsNameRow(student = it)
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    LazyColumn(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        contentPadding = PaddingValues(top = 1.dp, start = 1.dp, bottom = 2.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        items(students) { student ->
                            Row {
                                val studentSkips = skips.filter { it.studentId == student.id!! }
                                subjects.forEach { subject ->
                                    val studentMarks =
                                        marks.filter { it.studentId == student.id!! && it.pairId == subject.id!! }
                                    val marksValues = studentMarks.map { it.value }
                                    val average = marksValues.average()
                                    TableCell(
                                        text =
                                            if (!average.isNaN()) {
                                                String.format(
                                                    "%.2f",
                                                    average,
                                                )
                                            } else {
                                                ""
                                            },
                                    )
                                }
                                TableCell(text = (studentSkips.count { it.reasonType == 1 } * 2).toString())
                                TableCell(text = (studentSkips.count { it.reasonType == 0 } * 2).toString())
                                TableCell(text = (studentSkips.count() * 2).toString())
                            }
                        }
                    }
                    Row {
                        Box(
                            modifier = Modifier.height(DEFAULT_TABLE_CELL_SIZE),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Итого",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Row {
                            TableCell(text = (skips.count { it.reasonType == 1 } * 2).toString())
                            TableCell(text = (skips.count { it.reasonType == 0 } * 2).toString())
                            TableCell(text = (skips.count() * 2).toString())
                        }
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun TableCell(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .size(DEFAULT_TABLE_CELL_SIZE)
                .padding(top = 1.dp, start = 1.dp)
                .background(MaterialTheme.colorScheme.onBackground),
    ) {
        Text(
            text = text,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 16.sp,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun StatisticTableHeader(
    subjects: List<Pair>,
    startPadding: Dp,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = DEFAULT_CORNER_CLIP,
                        topEnd = DEFAULT_CORNER_CLIP,
                    ),
                ).background(MaterialTheme.colorScheme.onBackground)
                .padding(start = startPadding)
                .wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
    ) {
        subjects.forEach {
            HeaderTextedItem(text = it.title)
        }
        listOf("По уважительной", "Не по уважительной", "Всего").forEach {
            HeaderTextedItem(text = it)
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun HeaderTextedItem(text: String) {
    Box(
        modifier =
            Modifier
                .rotateVertically(false)
                .height(DEFAULT_TABLE_CELL_SIZE),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = text,
            color = Color.Black,
            modifier =
                Modifier
                    .widthIn(max = 150.dp)
                    .padding(horizontal = 10.dp),
            softWrap = true,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            fontSize = 16.sp,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun StatisticsNameRow(student: People) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .height(DEFAULT_TABLE_CELL_SIZE)
                .background(MaterialTheme.colorScheme.onBackground),
    ) {
        Text(
            text = student.get(PeopleKReturnTypes.LASTNAME_INITIALS),
            textAlign = TextAlign.Start,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp),
        )
    }
}
