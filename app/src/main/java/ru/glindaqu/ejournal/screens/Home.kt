package ru.glindaqu.ejournal.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.modules.simpleCalendar.SimpleCalendar
import ru.glindaqu.ejournal.ui.components.optionsList.QuickOptionsList
import ru.glindaqu.ejournal.ui.components.timeWidget.TimeWidget
import ru.glindaqu.ejournal.viewModel.implementation.HomeViewModel

@Suppress("ktlint:standard:function-naming")
/**
 * @author glindaqu
 *
 * Функция домашнего экрана пользователя
 */
@Composable
fun Home() {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[HomeViewModel::class.java]

    val systemUiController = rememberSystemUiController()
    val onBackground = MaterialTheme.colorScheme.background

    LaunchedEffect(Unit) {
        launch {
            systemUiController.setStatusBarColor(onBackground)
        }
    }

    Column(
        modifier =
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = DEFAULT_HORIZONTAL_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        SimpleCalendar(
            shape = RoundedCornerShape(17.dp),
            itemSelectedColor = MaterialTheme.colorScheme.background,
            weekdayTitles = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"),
        ) { }

        // TODO: check if there is a memory leak
        TimeWidget()

        QuickOptionsList()
    }
}
