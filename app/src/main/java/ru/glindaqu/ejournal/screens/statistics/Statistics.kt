package ru.glindaqu.ejournal.screens.statistics

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.PeopleKReturnTypes
import ru.glindaqu.ejournal.database.room.tables.Skip
import ru.glindaqu.ejournal.screens.journal.table.Loading
import ru.glindaqu.ejournal.viewModel.implementation.StatisticsViewModel
import java.util.Calendar
import java.util.Date

enum class StatUIState {
    LOADING,
    READY,
}

fun Modifier.vertical() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2),
            )
        }
    }

@SuppressLint("UnrememberedMutableState")
@Suppress("ktlint:standard:function-naming")
@Composable
fun Statistics() {
    val calendar = Calendar.getInstance()
    val dateStart by remember {
        mutableStateOf(
            Date(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH),
            ),
        )
    }
    val dateEnd by remember {
        mutableStateOf(
            Date(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
            ),
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
            )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun Body(
    subjects: List<Pair>,
    students: List<People>,
    marks: List<Mark>,
    skips: List<Skip>,
) {
    var legendSize by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        StatisticTableHeader(subjects = subjects, startPadding = legendSize)
        Row {
            LazyColumn(
                modifier =
                    Modifier
                        .onGloballyPositioned {
                            legendSize = with(density) { it.size.width.toDp() }
                        }.background(MaterialTheme.colorScheme.onBackground),
            ) {
                items(students) {
                    StatisticsNameRow(student = it)
                }
            }
            LazyColumn {
                items(students) { student ->
                    Row {
                        subjects.forEach { subject ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.size(DEFAULT_TABLE_CELL_SIZE + 10.dp),
                            ) {
                                Text(
                                    text =
                                        String.format(
                                            "%.2f",
                                            marks
                                                .filter {
                                                    it.studentId == student.id!! &&
                                                        it.pairId == subject.id!!
                                                }.map { it.value }
                                                .average(),
                                        ),
                                    modifier = Modifier,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black,
                                    fontSize = 18.sp,
                                )
                            }
                        }
                    }
                }
            }
        }
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
            Box(
                modifier = Modifier.wrapContentSize(),
            ) {
                Text(
                    text = it.title,
                    color = Color.Black,
                    modifier =
                        Modifier
                            .vertical()
                            .height(DEFAULT_TABLE_CELL_SIZE + 10.dp)
                            .rotate(-90f)
                            .padding(20.dp)
                            .height(20.dp),
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun StatisticsNameRow(student: People) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .height(DEFAULT_TABLE_CELL_SIZE + 10.dp)
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
