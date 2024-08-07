package ru.glindaqu.ejournal.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.ui.components.optionsList.QuickOptionsList
import ru.glindaqu.ejournal.ui.components.timeWidget.TimeWidget
import ru.glindaqu.ejournal.viewModel.implementation.HomeViewModel
import ru.glindaqu.ejournal.modules.simpleCalendar.SimpleCalendar

/**
 * @author glindaqu
 *
 * Функция домашнего экрана пользователя
 */
@Composable
fun Home() {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[HomeViewModel::class.java]

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(horizontal = DEFAULT_HORIZONTAL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SimpleCalendar(
            shape = RoundedCornerShape(17.dp),
            itemSelectedColor = MaterialTheme.colorScheme.background,
            weekdayTitles = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
        ) { }

        // TODO: check if there is a memory leak
        TimeWidget()

        QuickOptionsList()
    }
}