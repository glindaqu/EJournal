package ru.glindaqu.ejournal.modules.wheelDatePick

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberWheelDatePickState(): WheelDatePickState =
    remember {
        WheelDatePickState()
    }
