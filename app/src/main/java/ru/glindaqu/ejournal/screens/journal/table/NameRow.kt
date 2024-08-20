package ru.glindaqu.ejournal.screens.journal.table

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.database.room.tables.People

@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentsNames(
    students: List<People>,
    onGlobalPositioned: (size: LayoutCoordinates) -> Unit,
    item: @Composable (People) -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .shadow(elevation = 10.dp)
                .wrapContentSize()
                .onGloballyPositioned { coordinates ->
                    onGlobalPositioned(coordinates)
                },
        color = Color.White,
        shadowElevation = 10.dp,
    ) {
        Column(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP)),
        ) {
            for (i in students.indices) {
                item(students[i])
            }
        }
    }
}
