package ru.glindaqu.ejournal.screens.journal.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import ru.glindaqu.ejournal.dataModels.JournalRowData

@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentsNames(
    students: List<JournalRowData>,
    onGlobalPositioned: (size: LayoutCoordinates) -> Unit,
    item: @Composable (JournalRowData) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .wrapContentSize()
                .onGloballyPositioned { coordinates ->
                    onGlobalPositioned(coordinates)
                },
    ) {
        for (i in students.indices) {
            item(students[i])
        }
    }
}
