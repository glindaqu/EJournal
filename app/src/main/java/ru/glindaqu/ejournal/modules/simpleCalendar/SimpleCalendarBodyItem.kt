package ru.glindaqu.ejournal.modules.simpleCalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.modules.simpleCalendar.ui.theme.blue1

@Composable
fun SimpleCalendarBodyItem(
    text: String,
    isSelected: Boolean,
    selectedColor: Color = blue1,
    clickable: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .size(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable { clickable() }
            .background(
                color = if (isSelected) selectedColor else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        SimpleCalendarHeaderItem(it = text, selected = isSelected)
    }
}

