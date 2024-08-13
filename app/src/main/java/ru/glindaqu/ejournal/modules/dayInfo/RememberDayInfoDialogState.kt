package ru.glindaqu.ejournal.modules.dayInfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberDayInfoDialogState(): DayInfoDialogState =
    remember {
        DayInfoDialogState()
    }
