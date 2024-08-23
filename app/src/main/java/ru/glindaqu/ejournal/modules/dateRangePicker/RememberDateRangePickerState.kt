package ru.glindaqu.ejournal.modules.dateRangePicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberDateRangePickerState(): DateRangePickerState =
    remember {
        DateRangePickerState()
    }
