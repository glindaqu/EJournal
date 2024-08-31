package ru.glindaqu.ejournal.modules.dateRangePicker

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.modules.wheelDatePick.Location
import ru.glindaqu.ejournal.modules.wheelDatePick.MonthItem
import ru.glindaqu.ejournal.modules.wheelDatePick.PickSectionTitle
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnrememberedMutableState")
@Suppress("ktlint:standard:function-naming")
@Composable
fun DateRangePicker(
    state: DateRangePickerState,
    location: Location = Location.RU,
    onDateSelected: (Date, Date) -> Unit,
) {
    if (!state.showing()) {
        return
    }

    val calendar = Calendar.getInstance()

    var currentMonth by remember { mutableIntStateOf(Date().month) }

    var startDate by remember { mutableStateOf(state.defaultStartDate) }
    var endDate by remember { mutableStateOf(state.defaultEndDate) }

    val daysInMonth by derivedStateOf {
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val lazyListState = rememberLazyListState()

    val isConfirmEnabled by derivedStateOf {
        when {
            endDate < startDate -> false
            else -> true
        }
    }

    LaunchedEffect(Unit) {
        launch {
            lazyListState.scrollToItem(currentMonth - 1)
        }
    }

    Dialog(onDismissRequest = { state.close() }) {
        Card(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
        ) {
            Column(
                modifier =
                    Modifier.padding(
                        horizontal = DEFAULT_HORIZONTAL_PADDING,
                        vertical = DEFAULT_VERTICAL_PADDING,
                    ),
            ) {
                PickSectionTitle(title = "Месяц")
                LazyRow(
                    state = lazyListState,
                ) {
                    itemsIndexed(location.months) { index, item ->
                        MonthItem(
                            month = item,
                            monthIndex = index,
                            isSelect = currentMonth == index,
                            onClick = {
                                currentMonth = it
                            },
                        )
                    }
                }
                PickSectionTitle(title = "Число")
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    ((1..daysInMonth).toList()).forEach { i ->
                        val currentDate = Date(calendar.get(Calendar.YEAR), currentMonth, i)
                        DayItem(
                            date = currentDate,
                            isSelect = currentDate in startDate..endDate || startDate == currentDate,
                            onClick = {
                                if (startDate.time == 0L || endDate.time != 0L || startDate >= it) {
                                    startDate = it
                                    endDate = Date(0)
                                } else {
                                    endDate = it
                                }
                            },
                        )
                    }
                }
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Button(
                        onClick = {
                            onDateSelected(startDate, endDate)
                            state.setDefaults(startDate, endDate)
                            state.close()
                        },
                        enabled = isConfirmEnabled,
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onBackground,
                                disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                            ),
                        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                    ) {
                        Text(
                            text = "OK",
                            fontSize = 16.sp,
                            color = if (isConfirmEnabled) MaterialTheme.colorScheme.primary else Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun DayItem(
    date: Date,
    isSelect: Boolean,
    onClick: (Date) -> Unit,
) {
    val cardColor =
        if (isSelect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val rippleColor =
        if (!isSelect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val textColor = if (isSelect) Color.White else Color.Black
    Card(
        modifier =
            Modifier
                .padding(3.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = rippleColor),
                ) { onClick(date) },
        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
        colors = CardDefaults.cardColors(containerColor = cardColor),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(45.dp),
        ) {
            Text(
                text = date.date.toString(),
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
        }
    }
}
