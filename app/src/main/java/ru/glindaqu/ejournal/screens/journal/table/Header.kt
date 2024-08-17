package ru.glindaqu.ejournal.screens.journal.table

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE

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
