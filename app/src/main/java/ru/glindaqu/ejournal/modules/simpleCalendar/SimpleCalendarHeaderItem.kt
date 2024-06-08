package ru.glindaqu.ejournal.modules.simpleCalendar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SimpleCalendarHeaderItem(it: String, color: Color = Color.Black, selected: Boolean = false) {
    Text(
        text = it,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color = color,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        modifier = Modifier
            .padding(2.dp)
    )
}