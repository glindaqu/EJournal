package ru.glindaqu.ejournal.modules.dayInfo

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.viewModel.implementation.JournalViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("StateFlowValueCalledInComposition")
@Suppress("ktlint:standard:function-naming")
@Composable
fun DayInfoDialog(
    state: DayInfoDialogState,
    addMark: (Int) -> Unit,
    deleteMark: (Mark) -> Unit,
) {
    if (!state.show) return
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[JournalViewModel::class.java]
    val marks by viewModel
        .getAllMarksBy(
            state.date,
            state.studentId,
            viewModel.pickedSubject.value.id!!,
        ).collectAsState(
            initial = listOf(),
        )
    Dialog(onDismissRequest = { state.show = false }) {
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                    .background(MaterialTheme.colorScheme.onBackground),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(
                            horizontal = DEFAULT_HORIZONTAL_PADDING,
                            vertical = DEFAULT_VERTICAL_PADDING,
                        ).wrapContentSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "${
                        SimpleDateFormat(
                            "d MMMM",
                            Locale("ru"),
                        ).format(state.date)
                    }, ${state.studentName}",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
                Spacer(
                    modifier =
                        Modifier
                            .padding(bottom = 10.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)
                            .alpha(0.2f),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    LabeledRadioButton(selected = !state.isAbsence, text = "Был(а)") {
                        state.isAbsence = !state.isAbsence
                    }
                    LabeledRadioButton(selected = state.isAbsence, text = "Не был(а)") {
                        state.isAbsence = !state.isAbsence
                    }
                }

                Text(
                    text = "Оценки",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )

                Row {
                    for (i in marks.indices) {
                        MarkItemDisabled(value = marks[i], index = i) {
                            deleteMark(marks[i])
                        }
                    }
                    if (marks.isEmpty()) {
                        Text(
                            color = Color.Gray,
                            text = "Нет оценок...",
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.alpha(0.7f),
                        )
                    }
                }

                Row {
                    for (i in 2..5) {
                        MarkItemClickable(value = i) {
                            addMark(i)
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Button(
                        onClick = { state.show = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    ) {
                        Text(
                            text = "OK",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun LabeledRadioButton(
    selected: Boolean,
    text: String,
    click: () -> Unit,
) {
    val bg =
        if (!selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary
    val textColor = if (!selected) Color.Black else Color.White
    Box(
        modifier =
            Modifier
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(bg)
                .clickable { click() },
    ) {
        Text(text = text, color = textColor, fontSize = 18.sp, modifier = Modifier.padding(10.dp))
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun MarkItemClickable(
    value: Int,
    click: (Int) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .wrapContentSize()
                .padding(3.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    click(value)
                }.padding(5.dp),
    ) {
        Text(text = value.toString(), fontSize = 20.sp)
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun MarkItemDisabled(
    value: Mark,
    index: Int,
    click: (Int) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .wrapContentSize()
                .padding(2.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(MaterialTheme.colorScheme.background)
                .padding(10.dp)
                .clickable {
                    click(index)
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = value.value.toString(),
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}
