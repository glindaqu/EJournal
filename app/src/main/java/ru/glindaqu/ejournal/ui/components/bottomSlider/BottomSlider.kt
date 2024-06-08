package ru.glindaqu.ejournal.ui.components.bottomSlider


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.R

@Composable
fun BottomSlider() {
    LazyRow(
        modifier = Modifier.padding(vertical = 15.dp).background(Color.Transparent)
    ) {
        item {
            BottomSliderItem(
                textSrc = R.string.bot_bar_slider_students,
                painterSrc = R.drawable.peoples
            ) {}
        }
        item {
            BottomSliderItem(
                textSrc = R.string.bot_bar_slider_subjects,
                painterSrc = R.drawable.subjects
            ) {}
        }
        item {
            BottomSliderItem(
                textSrc = R.string.bot_bar_slider_settings,
                painterSrc = R.drawable.settings
            ) {}
        }
    }
}