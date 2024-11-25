package ru.glindaqu.ejournal.screens.detail

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.PeopleKReturnTypes
import ru.glindaqu.ejournal.viewModel.implementation.DetailViewModel
import java.util.Calendar
import java.util.Date
import co.yml.charts.ui.linechart.model.IntersectionPoint as IntersectionPoint1

@Suppress("ktlint:standard:function-naming")
@Composable
fun Detail(
    studentId: Int,
    onClose: () -> Unit,
) {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[DetailViewModel::class.java]
    val student by viewModel.getStudentById(studentId).collectAsState(initial = null)

    val color = MaterialTheme.colorScheme.onBackground
    val uiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        uiController.setStatusBarColor(color)
    }

    when {
        student == null -> Text(text = "Loading")
        else -> Body(student = student!!, onClose = onClose)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("DefaultLocale", "UnusedMaterial3ScaffoldPaddingParameter")
@Suppress("ktlint:standard:function-naming")
@Composable
private fun Body(
    student: People,
    onClose: () -> Unit,
) {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[DetailViewModel::class.java]
    var bodyType by remember { mutableStateOf(BodyType.MARKS) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = student.get(PeopleKReturnTypes.FULL_NAME)) },
                navigationIcon = {
                    IconButton(onClick = { onClose() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = Color.Black,
                        )
                    }
                },
                modifier =
                    Modifier.clip(
                        RoundedCornerShape(
                            bottomEnd = DEFAULT_CORNER_CLIP,
                            bottomStart = DEFAULT_CORNER_CLIP,
                        ),
                    ),
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = DEFAULT_HORIZONTAL_PADDING,
                        vertical = DEFAULT_VERTICAL_PADDING,
                    ).padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Switcher(onStateChanged = { bodyType = it }, selected = bodyType)
            when (bodyType) {
                BodyType.MARKS -> MarkContent(viewModel = viewModel, student = student)
                BodyType.SKIPS -> SkipsContent(viewModel = viewModel, student = student)
                else -> OverviewContent(viewModel = viewModel, student = student)
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Suppress("ktlint:standard:function-naming")
@Composable
private fun OverviewContent(
    viewModel: DetailViewModel,
    student: People,
) {
    val marks by viewModel.getAllMarksByStudent(student.id!!).collectAsState(initial = listOf())
    val skips by viewModel.getAllSkipsByAllTime(student.id!!).collectAsState(initial = listOf())
    Column(modifier = Modifier.padding(vertical = DEFAULT_VERTICAL_PADDING)) {
        Item(
            title = "Общий средний бал",
            textContent = String.format("%.2f", marks.map { it.value }.average()),
        )
        Item(
            title = "Общее кол-во пропусков",
            textContent =
                "${skips.filter { it.reasonType == 0 }.size * 2}/" + "${skips.filter { it.reasonType == 1 }.size * 2}/" +
                    "${skips.size * 2}",
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SkipsContent(
    viewModel: DetailViewModel,
    student: People,
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val subjects by viewModel.getAllSubjects().collectAsState(initial = listOf())
    val skips by viewModel
        .getAllSkipsByStudent(
            student.id!!,
            Date(year, month, calendar.getActualMinimum(Calendar.DAY_OF_MONTH)).time,
            Date(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).time,
        ).collectAsState(
            initial = listOf(),
        )
    Column(modifier = Modifier.padding(vertical = DEFAULT_VERTICAL_PADDING)) {
        subjects.forEach { subject ->
            val currentSkips = skips.filter { skip -> skip.pairId == subject.id }
            val skipsRespectful = currentSkips.filter { skip -> skip.reasonType == 1 }
            val skipsDisrespectful = currentSkips.filter { skip -> skip.reasonType == 0 }
            SkipItem(
                title = subject.title,
                respectful = skipsRespectful.size,
                skip = skipsDisrespectful.size,
            )
        }
    }
    Plot(viewModel, student)
}

private enum class BodyType {
    MARKS,
    SKIPS,
    OVERVIEW,
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun Switcher(
    onStateChanged: (BodyType) -> Unit,
    selected: BodyType,
) {
    Row(
        modifier =
            Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(MaterialTheme.colorScheme.onBackground)
                .clip(
                    RoundedCornerShape(DEFAULT_CORNER_CLIP),
                ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SwitcherItem(
            optionText = "Оценки",
            onStateChanged = { onStateChanged(BodyType.MARKS) },
            selected = selected == BodyType.MARKS,
        )
        SwitcherItem(
            optionText = "Пропуски",
            onStateChanged = { onStateChanged(BodyType.SKIPS) },
            selected = selected == BodyType.SKIPS,
        )
        SwitcherItem(
            optionText = "Общее",
            onStateChanged = { onStateChanged(BodyType.OVERVIEW) },
            selected = selected == BodyType.OVERVIEW,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SwitcherItem(
    optionText: String,
    selected: Boolean,
    onStateChanged: () -> Unit,
) {
    val textColor = if (selected) Color.White else Color.Black
    val color =
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    Box(
        modifier =
            Modifier
                .clickable {
                    onStateChanged()
                }.padding(3.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(color)
                .padding(7.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = optionText, color = textColor, fontSize = 22.sp)
    }
}

@SuppressLint("DefaultLocale")
@Suppress("ktlint:standard:function-naming")
@Composable
private fun MarkContent(
    viewModel: DetailViewModel,
    student: People,
) {
    val subjects by viewModel.getAllSubjects().collectAsState(initial = listOf())
    val marks by viewModel.getAllMarksByStudent(student.id!!).collectAsState(initial = listOf())
    Column(modifier = Modifier.padding(vertical = DEFAULT_VERTICAL_PADDING)) {
        subjects.forEach { subject ->
            val average =
                marks.filter { mark -> mark.pairId == subject.id!! }.map { it.value }.average()
            Item(
                title = subject.title,
                textContent =
                    if (average.isNaN()) {
                        "Нет оценок"
                    } else {
                        String.format(
                            "%.2f",
                            average,
                        )
                    },
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun Item(
    title: String,
    textContent: String,
) {
    Row(
        modifier =
            Modifier
                .padding(
                    0.5.dp,
                ).fillMaxWidth()
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(0.6f)) {
            Text(text = title, color = Color.Black)
        }

        Box(modifier = Modifier.weight(0.4f), contentAlignment = Alignment.CenterEnd) {
            Text(
                text = textContent,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SkipItem(
    title: String,
    respectful: Int,
    skip: Int,
) {
    Row(
        modifier =
            Modifier
                .padding(
                    0.5.dp,
                ).fillMaxWidth()
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(0.7f)) {
            Text(text = title, color = Color.Black)
        }

        Row(modifier = Modifier.weight(0.4f), horizontalArrangement = Arrangement.End) {
            Box(contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "${skip * 2}/",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
            Box(contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "${respectful * 2}/",
                    color = Color(0xFFFFA443),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
            Box(contentAlignment = Alignment.CenterEnd) {
                Text(
                    text = "${(skip + respectful) * 2}",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun Plot(
    viewModel: DetailViewModel,
    student: People,
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val skips by viewModel
        .getAllSkipsByStudent(
            student.id!!,
            Date(year, month, calendar.getActualMinimum(Calendar.DAY_OF_MONTH)).time,
            Date(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).time,
        ).collectAsState(
            initial = listOf(),
        )
    val pointsData: MutableList<Point> =
        mutableListOf()
    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    var maxSkipped = Int.MIN_VALUE
    for (i in 1..maxDay) {
        val skipped = skips.filter { it.date == Date(year, month, i).time }.size
        if (skipped > maxSkipped) maxSkipped = skipped
        pointsData.add(Point(i.toFloat(), skipped.toFloat()))
    }

    val xAxisData =
        AxisData
            .Builder()
            .axisStepSize(40.dp)
            .backgroundColor(Color.White)
            .steps(pointsData.size - 1)
            .labelData { i -> i.toString() }
            .labelAndAxisLinePadding(15.dp)
            .build()

    val yAxisData =
        AxisData
            .Builder()
            .steps(maxSkipped)
            .backgroundColor(Color.White)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                i.toString()
            }.build()
    val lineChartData =
        LineChartData(
            linePlotData =
                LinePlotData(
                    lines =
                        listOf(
                            Line(
                                dataPoints = pointsData,
                                lineStyle = LineStyle(color = MaterialTheme.colorScheme.primary),
                                intersectionPoint = IntersectionPoint1(color = MaterialTheme.colorScheme.primary),
                                shadowUnderLine = ShadowUnderLine(color = MaterialTheme.colorScheme.primary),
                            ),
                        ),
                ),
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(color = MaterialTheme.colorScheme.background),
            backgroundColor = Color.White,
        )
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = DEFAULT_VERTICAL_PADDING),
    ) {
        Text(
            text = "Пропуски на текущий месяц",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
    LineChart(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP)),
        lineChartData = lineChartData,
    )
}
