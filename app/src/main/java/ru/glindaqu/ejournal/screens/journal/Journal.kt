package ru.glindaqu.ejournal.screens.journal

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.modules.dayInfo.DayInfoDialog
import ru.glindaqu.ejournal.modules.dayInfo.rememberDayInfoDialogState
import ru.glindaqu.ejournal.modules.subjectPick.SubjectPick
import ru.glindaqu.ejournal.modules.subjectPick.rememberSubjectPickState
import ru.glindaqu.ejournal.modules.wheelDatePick.WheelDatePick
import ru.glindaqu.ejournal.modules.wheelDatePick.rememberWheelDatePickState
import ru.glindaqu.ejournal.screens.journal.helpers.scrollHeaderToDay
import ru.glindaqu.ejournal.screens.journal.helpers.toPx
import ru.glindaqu.ejournal.screens.journal.table.TableBody
import ru.glindaqu.ejournal.screens.journal.table.TableHeader
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@Suppress("ktlint:standard:function-naming")
@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@Composable
fun Journal(navHostController: NavHostController) {
    val calendar = Calendar.getInstance()

    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]
    val date by viewModel.selectedDate.collectAsState()
    val subject by viewModel.pickedSubject.collectAsState()
    val density = LocalDensity.current
    var studentListWidth by remember { mutableStateOf(0.dp) }
    val hScrollState = rememberScrollState()
    val offsetPx = DEFAULT_TABLE_CELL_SIZE.toPx()

    val systemUiController = rememberSystemUiController()
    val onBackground = MaterialTheme.colorScheme.background

    val displayOnlySurname =
        derivedStateOf {
            when {
                hScrollState.value <= 0 -> false
                else -> true
            }
        }

    val wheelDatePickState = rememberWheelDatePickState()
    val dayInfoDialogState = rememberDayInfoDialogState()
    val subjectPickState = rememberSubjectPickState()

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        launch {
            scrollHeaderToDay(LocalDate.now().dayOfMonth - 1, offsetPx, hScrollState)
            systemUiController.setStatusBarColor(onBackground)
        }
    }

    subjectPickState.subjectsList =
        viewModel.getSubjects().collectAsState(initial = listOf(Pair())).value

    WheelDatePick(
        state = wheelDatePickState,
        defaultDate = date,
    ) {
        viewModel.selectedDate.value = it
        coroutine.launch {
            scrollHeaderToDay(Date(it).date - 1, offsetPx, hScrollState)
        }
    }
    SubjectPick(state = subjectPickState) {
        viewModel.pickedSubject.value = it
    }
    DayInfoDialog(
        state = dayInfoDialogState,
        addMark = {
            viewModel.addMark(
                date = dayInfoDialogState.date,
                pairId = subject.id!!,
                mark = it,
                studentId = dayInfoDialogState.studentId,
            )
        },
        deleteMark = {
            viewModel.deleteMarkBy(it)
        },
        addSkip = {
            viewModel.addSkip(
                subject.id!!,
                dayInfoDialogState.date,
                dayInfoDialogState.studentId,
                it,
            )
        },
        deleteSkip = {
            viewModel.deleteSkip(it.uid!!)
        },
    )

    Column {
        TopJournalBar(
            wheelDatePickState = wheelDatePickState,
            subjectPickState = subjectPickState,
            selectedDate = date,
            selectedSubject = subject.title,
        )
        Column(
            modifier =
                Modifier.clip(
                    RoundedCornerShape(
                        topStart = DEFAULT_CORNER_CLIP,
                        topEnd = DEFAULT_CORNER_CLIP,
                    ),
                ),
        ) {
            TableHeader(
                daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH),
                padding = studentListWidth,
                scrollState = hScrollState,
            )
            TableBody(
                displayOnlySurname = displayOnlySurname.value,
                scrollState = hScrollState,
                dialogState = dayInfoDialogState,
                navHostController = navHostController,
            ) {
                studentListWidth = with(density) { it.size.width.toDp() }
            }
        }
    }
}
