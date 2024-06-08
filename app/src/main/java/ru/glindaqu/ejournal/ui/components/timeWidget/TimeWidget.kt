package ru.glindaqu.ejournal.ui.components.timeWidget

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.glindaqu.ejournal.modules.simpleCalendar.hoursAndMinutes
import java.util.Date

@Composable
fun TimeWidget() {
    var timeState by remember {
        mutableStateOf(hoursAndMinutes.format(Date()))
    }
    lateinit var timerRunner: Runnable
    val handler = Handler(Looper.getMainLooper())

    timerRunner = Runnable {
        timeState = hoursAndMinutes.format(Date())
        handler.postDelayed(timerRunner, 1_000)
    }

    Text(
        text = timeState,
        fontSize = 40.sp,
        color = Color.Black,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
    )

    DisposableEffect(Unit) {
        onDispose {
            handler.removeCallbacks(timerRunner)
        }
    }

    handler.postDelayed(timerRunner, 1_000)
}