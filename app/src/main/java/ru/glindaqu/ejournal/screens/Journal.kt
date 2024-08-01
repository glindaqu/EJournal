package ru.glindaqu.ejournal.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel
import java.util.Calendar
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import androidx.compose.ui.unit.dp

@Composable
fun Journal() {

    val calendar: Calendar = Calendar.getInstance()
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    var studentListWidth by remember { mutableStateOf(0.dp) }

    val hScrollState = rememberScrollState()
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]

    val density = LocalDensity.current

    Column {
        Row {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Select Date")
            }
        }
        Column(
            modifier = Modifier
                .horizontalScroll(hScrollState)
        ) {
            TableHeader(
                daysInMonth = daysInMonth,
                padding = studentListWidth,
            )
            Row(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                StudentsList(
                    viewModel = viewModel,
                    onGlobalPositioned = {
                        studentListWidth = with(density) { it.size.width.toDp() }
                    }
                )
            }
        }
    }
}

@Composable
fun TableHeader(daysInMonth: Int, padding: Dp) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(start = padding),
    ) {
        for (i in 1..daysInMonth) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(DEFAULT_TABLE_CELL_SIZE),
            ) {
                Text(
                    text = i.toString(),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun StudentsList(
    viewModel: JournalViewModel,
    onGlobalPositioned: (size: LayoutCoordinates) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .wrapContentSize()
            .onGloballyPositioned { coordinates ->
                onGlobalPositioned(coordinates)
            }
    ) {
        for (i in 0 ..< viewModel.studentsList.size) {
            Text(text = viewModel.studentsList[i])
        }
    }
}
