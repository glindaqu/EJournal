package ru.glindaqu.ejournal.screens.journal.table

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE

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
