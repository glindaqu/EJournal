package ru.glindaqu.ejournal.modules.wheelDatePick

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import java.util.Calendar
import java.util.Date

open class Location(val months: List<String>) {
    companion object {
        val ENG = Location(
            listOf(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            )
        )
        val RU = Location(
            listOf(
                "Январь",
                "Февраль",
                "Март",
                "Апрель",
                "Май",
                "Июнь",
                "Июль",
                "Август",
                "Сентябрь",
                "Октябрь",
                "Ноябрь",
                "Декабрь"
            )
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WheelDatePick(
    state: WheelDatePickState,
    location: Location = Location.RU,
    defaultDate: Date = Date(),
    onDateSelected: (Date) -> Unit
) {
    if (!state.show) {
        return
    }
    var month by remember { mutableIntStateOf(defaultDate.month) }
    var day by remember { mutableIntStateOf(defaultDate.date) }
    val calendar = Calendar.getInstance()
    val daysInMonth by derivedStateOf {
        calendar.set(Calendar.MONTH, month)
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
    val isConfirmEnabled by derivedStateOf {
        when {
            day > daysInMonth -> false
            else -> true
        }
    }

    Dialog(onDismissRequest = { state.show = false }) {
        Card(
            modifier = Modifier.wrapContentHeight(),
            shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = DEFAULT_HORIZONTAL_PADDING, vertical = DEFAULT_VERTICAL_PADDING
                )
            ) {
                PickSectionTitle(title = "Месяц")
                FlowRow {
                    for (i in location.months.indices) {
                        MonthItem(month = location.months[i],
                            monthIndex = i,
                            isSelect = month == i,
                            onClick = {
                                month = it
                            })
                    }
                }
                PickSectionTitle(title = "Число")
                FlowRow(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    for (i in 1..daysInMonth) {
                        DayItem(day = i, isSelect = day == i, onClick = {
                            day = it
                        })
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = {
                            onDateSelected(
                                Date(
                                    calendar.get(Calendar.YEAR), month, day
                                )
                            )
                            state.show = false
                        }, enabled = isConfirmEnabled, colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                        )
                    ) {
                        Text(
                            text = "OK",
                            fontSize = 16.sp,
                            color = if (isConfirmEnabled) MaterialTheme.colorScheme.primary else Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun PickSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(7.dp)
            .padding(top = 7.dp)
    )
}

@Composable
internal fun MonthItem(month: String, monthIndex: Int, isSelect: Boolean, onClick: (Int) -> Unit) {
    val cardColor =
        if (isSelect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val rippleColor =
        if (!isSelect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val textColor = if (isSelect) Color.White else Color.Black
    Card(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = rippleColor)
            ) {
                onClick(monthIndex)
            },
        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Text(
            text = month,
            color = textColor,
            modifier = Modifier.padding(7.dp),
            fontSize = 18.sp,
        )
    }
}

@Composable
internal fun DayItem(day: Int, isSelect: Boolean, onClick: (Int) -> Unit) {
    val cardColor =
        if (isSelect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val rippleColor =
        if (!isSelect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val textColor = if (isSelect) Color.White else Color.Black
    Card(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = rippleColor)
            ) { onClick(day) },
        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
        colors = CardDefaults.cardColors(containerColor = cardColor),
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .size(45.dp)
        ) {
            Text(
                text = day.toString(),
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}