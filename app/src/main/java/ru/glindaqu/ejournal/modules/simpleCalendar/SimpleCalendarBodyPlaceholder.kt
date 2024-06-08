package ru.glindaqu.ejournal.modules.simpleCalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimpleCalendarBodyPlaceholder() {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .size(50.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        SimpleCalendarHeaderItem(it = "1", color = Color.White)
    }
}