package ru.glindaqu.ejournal.screens.journal.table

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_TABLE_CELL_SIZE
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.PeopleKReturnTypes

@Suppress("ktlint:standard:function-naming")
@Composable
fun StudentsNames(
    students: List<People>,
    onGlobalPositioned: (size: LayoutCoordinates) -> Unit,
    isDisplayOnlySurname: Boolean = false,
    onClick: (People) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .wrapContentSize()
                .onGloballyPositioned { coordinates ->
                    onGlobalPositioned(coordinates)
                }.clip(RoundedCornerShape(DEFAULT_CORNER_CLIP)),
    ) {
        for (i in students.indices) {
            val it = students[i]
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .padding(top = 1.dp)
                        .height(DEFAULT_TABLE_CELL_SIZE - 2.dp)
                        .background(MaterialTheme.colorScheme.onBackground)
                        .clickable { onClick(it) },
            ) {
                Text(
                    text =
                        it.get(
                            if (isDisplayOnlySurname) {
                                PeopleKReturnTypes.LASTNAME
                            } else {
                                PeopleKReturnTypes.FULL_NAME
                            },
                        ),
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp),
                )
            }
        }
    }
}
