package ru.glindaqu.ejournal.modules.subjectPick

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalLayoutApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun SubjectPick(state: SubjectPickState) {
    if (!state.isOpen()) return
    Dialog(onDismissRequest = { state.close() }) {
        Column(modifier = Modifier.wrapContentSize()) {
            Text(text = "Предмет")
            FlowRow {
                for (i in state.subjectsList.indices) {
                    val element = state.subjectsList[i]
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = element)
                    }
                }
            }
        }
    }
}
