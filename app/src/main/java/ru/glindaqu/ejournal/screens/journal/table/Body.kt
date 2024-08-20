package ru.glindaqu.ejournal.screens.journal.table

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.modules.dayInfo.DayInfoDialogState
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel

enum class JournalTableBodyState {
    LOADING,
    READY,
    WAITING,
}

@SuppressLint("UnrememberedMutableState")
@Suppress("ktlint:standard:function-naming")
@Composable
fun TableBody(
    displayOnlySurname: Boolean,
    scrollState: ScrollState,
    dialogState: DayInfoDialogState,
    reassignStudentsListWidth: (LayoutCoordinates) -> Unit,
) {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]
    val studentsList by viewModel
        .getAllStudents()
        .collectAsState(
            initial = listOf(),
        )
    val subject by viewModel.pickedSubject.collectAsState()
    val uiState by derivedStateOf {
        when {
            studentsList.isEmpty() -> JournalTableBodyState.LOADING
            subject.id == null -> JournalTableBodyState.WAITING
            else -> JournalTableBodyState.READY
        }
    }

    when (uiState) {
        JournalTableBodyState.READY ->
            TableBodyContent(
                displayOnlySurname = displayOnlySurname,
                studentsList = studentsList,
                reassignStudentsListWidth = reassignStudentsListWidth,
            ) {
                StudentStats(
                    scrollState = scrollState,
                    dialogState = dialogState,
                )
            }

        JournalTableBodyState.WAITING ->
            Row {
                TableBodyContent(
                    displayOnlySurname = displayOnlySurname,
                    studentsList = studentsList,
                    reassignStudentsListWidth = reassignStudentsListWidth,
                )
                JournalInfoPlaceholder()
            }

        JournalTableBodyState.LOADING -> Loading()
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun JournalInfoPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Выберите предмет!",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun TableBodyContent(
    displayOnlySurname: Boolean,
    studentsList: List<People>,
    reassignStudentsListWidth: (LayoutCoordinates) -> Unit,
    content: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier =
            Modifier
                .background(MaterialTheme.colorScheme.onBackground)
                .verticalScroll(
                    rememberScrollState(),
                ),
    ) {
        StudentsNames(
            students = studentsList,
            onGlobalPositioned = { reassignStudentsListWidth(it) },
            item = {
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
                                it.lastname
                            } else {
                                "${it.lastname} " + "${it.name} " + it.patronymic
                            },
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp),
                    )
                }
            },
        )
        content?.invoke()
    }
}
