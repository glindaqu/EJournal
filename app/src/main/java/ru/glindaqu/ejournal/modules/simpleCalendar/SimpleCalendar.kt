package ru.glindaqu.ejournal.modules.simpleCalendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.modules.simpleCalendar.ui.theme.blue1
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

/**
 * Функция виджета-календаря, обычно занимает весь экарн и позволяет отслеживать нажатия на
 * конкретные числа. Подгрузка текущего числа, дат и их смещение происходит автоматически,
 * в зависимости от локальной даты пользователя.
 *
 * @param[weekdayTitles] Заголовки шапки календаря. Представляет [List] типа [String]
 *
 * @param[backgroundColor] Цвет фона виджета. Принимает цвета типа
 * [androidx.compose.ui.graphics.Color]. По умолчанию [Color.White]
 *
 *
 */
@SuppressLint("SimpleDateFormat")
@Composable
fun SimpleCalendar(
    weekdayTitles: List<String> = listOf("mon", "tue", "wed", "thu", "fri", "sat", "sun"),
    backgroundColor: Color = Color.White,
    elevation: Dp = 10.dp,
    shape: RoundedCornerShape = RoundedCornerShape(5.dp),
    spacerLineColor: Color = Color.Black,
    itemSelectedColor: Color = blue1,
    onDateSelected: (date: Date) -> Unit
) {
    val currentDay = dayOnly.format(Date()).toInt()
    val month = LocalDate.now().month
    val dayOffset = LocalDate.of(LocalDate.now().year, month, 1).dayOfWeek.value
    Card(
        elevation = CardDefaults.cardElevation(elevation),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .height(30.dp),
                columns = GridCells.Fixed(7),
            ) {
                items(weekdayTitles) {
                    SimpleCalendarHeaderItem(it = it)
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .padding(bottom = 3.dp)
                    .height(0.5.dp)
                    .background(spacerLineColor)
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(7),
                verticalItemSpacing = 10.dp,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .height(300.dp)
            ) {
                items((1..<dayOffset).toList()) {
                    SimpleCalendarBodyPlaceholder()
                }
                items((1..month.length(true)).toList()) {
                    SimpleCalendarBodyItem(
                        text = it.toString(),
                        isSelected = it == currentDay,
                        selectedColor = itemSelectedColor,
                        clickable = {
                            val date =
                                SimpleDateFormat("M-d-yyyy").parse("${month.value}-$it-${LocalDate.now().year}")
                            onDateSelected(date ?: Date())
                        }
                    )
                }
            }
        }
    }
}