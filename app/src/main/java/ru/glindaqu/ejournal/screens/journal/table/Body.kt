package ru.glindaqu.ejournal.screens.journal.table

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.modules.dayInfo.DayInfoDialogState
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun TableBody(
    displayOnlySurname: Boolean,
    scrollState: ScrollState,
    dialogState: DayInfoDialogState,
    reassignStudentsListWidth: (LayoutCoordinates) -> Unit,
) {
    val studentsList by ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]
        .getAllStudents()
        .collectAsState(
            initial = listOf(),
        )
    Row(
        modifier =
            Modifier.background(MaterialTheme.colorScheme.onBackground).verticalScroll(
                rememberScrollState(),
            ),
    ) {
        StudentsNames(students = studentsList, onGlobalPositioned = {
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
        })
        StudentStats(
            scrollState = scrollState,
            dialogState = dialogState,
        )
    }
}
