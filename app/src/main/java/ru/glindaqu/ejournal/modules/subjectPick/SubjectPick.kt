package ru.glindaqu.ejournal.modules.subjectPick

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.database.room.tables.Pair

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalLayoutApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun SubjectPick(
    state: SubjectPickState,
    onSubjectSelected: (Pair) -> Unit,
) {
    if (!state.isOpen()) return
    val isOKBtnEnable by derivedStateOf { state.subject.id != null }
    val textColor = if (isOKBtnEnable) MaterialTheme.colorScheme.primary else Color.White
    Dialog(onDismissRequest = { state.close() }) {
        Column(
            modifier =
                Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                    .background(MaterialTheme.colorScheme.onBackground),
        ) {
            Column(
                modifier =
                    Modifier.padding(
                        vertical = DEFAULT_VERTICAL_PADDING,
                        horizontal = DEFAULT_HORIZONTAL_PADDING,
                    ),
            ) {
                Text(
                    text = "Предмет",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier =
                        Modifier
                            .padding(7.dp)
                            .padding(top = 7.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                )
                FlowRow(
                    modifier =
                        Modifier.heightIn(max = 400.dp).verticalScroll(
                            rememberScrollState(),
                        ),
                ) {
                    for (i in state.subjectsList.indices) {
                        val element = state.subjectsList[i]
                        Item(text = element.title, selected = element == state.subject) {
                            state.subject = element
                        }
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
                            state.close()
                            onSubjectSelected(state.subject)
                        },
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onBackground,
                                disabledContainerColor = MaterialTheme.colorScheme.errorContainer,
                            ),
                        enabled = isOKBtnEnable,
                    ) {
                        Text(
                            text = "OK",
                            fontSize = 16.sp,
                            color = textColor,
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
internal fun Item(
    text: String,
    selected: Boolean,
    click: () -> Unit,
) {
    val cardColor =
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val rippleColor =
        if (!selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
    val textColor = if (selected) Color.White else Color.Black
    Card(
        modifier =
            Modifier
                .wrapContentSize()
                .padding(3.dp)
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = rippleColor),
                ) {
                    click()
                },
        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
        colors = CardDefaults.cardColors(containerColor = cardColor),
    ) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(7.dp).wrapContentSize(),
            fontSize = 18.sp,
        )
    }
}
