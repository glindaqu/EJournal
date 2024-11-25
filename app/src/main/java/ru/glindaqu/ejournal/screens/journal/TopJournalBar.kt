package ru.glindaqu.ejournal.screens.journal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.modules.subjectPick.SubjectPickState
import ru.glindaqu.ejournal.modules.wheelDatePick.WheelDatePickState
import java.text.SimpleDateFormat
import java.util.Locale

@Suppress("ktlint:standard:function-naming")
@Composable
fun TopJournalBar(
    wheelDatePickState: WheelDatePickState,
    subjectPickState: SubjectPickState,
    selectedDate: Long,
    selectedSubject: String,
) {
    Row(
        modifier =
            Modifier.padding(
                horizontal = DEFAULT_HORIZONTAL_PADDING / 2,
                vertical = DEFAULT_VERTICAL_PADDING / 2,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Button(
            onClick = { wheelDatePickState.show = true },
            shape =
                RoundedCornerShape(
                    DEFAULT_CORNER_CLIP,
                ),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier =
                Modifier.shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                    clip = true,
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                ),
        ) {
            Text(
                text = SimpleDateFormat("d MMMM", Locale("ru")).format(selectedDate),
                fontSize = 18.sp,
            )
        }
        Button(
            onClick = { subjectPickState.show() },
            shape =
                RoundedCornerShape(
                    DEFAULT_CORNER_CLIP,
                ),
            modifier =
                Modifier.shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                    clip = true,
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                ),
        ) {
            Text(
                text = selectedSubject,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
