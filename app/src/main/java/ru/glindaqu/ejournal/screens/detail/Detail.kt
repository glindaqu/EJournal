package ru.glindaqu.ejournal.screens.detail

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.PeopleKReturnTypes
import ru.glindaqu.ejournal.viewModel.implementation.DetailViewModel
import java.util.Calendar
import java.util.Date

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
                    .fillMaxWidth()
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
            Item(
                title = subject.title,
                textContent = "${skipsRespectful.size * 2}/" + "${skipsDisrespectful.size * 2}/" + "${currentSkips.size * 2}",
            )
        }
    }
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
                .padding(3.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(color)
                .padding(7.dp)
                .clickable {
                    onStateChanged()
                },
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