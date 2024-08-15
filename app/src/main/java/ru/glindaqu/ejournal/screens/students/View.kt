package ru.glindaqu.ejournal.screens.students

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.navigation.Route
import ru.glindaqu.ejournal.viewModel.implementation.StudentsViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun ViewStudents(
    viewModel: StudentsViewModel,
    navHostController: NavHostController,
) {
    val students = viewModel.getStudents().collectAsState(initial = listOf()).value
    Scaffold(topBar = {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = DEFAULT_CORNER_CLIP,
                            bottomEnd = DEFAULT_CORNER_CLIP,
                        ),
                    ).background(MaterialTheme.colorScheme.onBackground),
            horizontalArrangement = Arrangement.End,
        ) {
            FloatingActionButton(
                onClick = { viewModel.uiState.value = StudentsUIState.ADD },
                modifier =
                    Modifier
                        .padding(end = DEFAULT_HORIZONTAL_PADDING / 2)
                        .padding(10.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                            clip = true,
                            ambientColor = Color.Black,
                            spotColor = Color.Black,
                        ).clip(RoundedCornerShape(DEFAULT_CORNER_CLIP)),
                shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }) { it ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier.padding(it).fillMaxHeight(),
        ) {
            items(students) { student ->
                Item(student = student) {
                    navHostController.navigate(Route.editStudent.get() + "/${student.id}")
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun Item(
    student: People,
    click: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = DEFAULT_HORIZONTAL_PADDING)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                    clip = true,
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                ).clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(
                    horizontal = DEFAULT_HORIZONTAL_PADDING,
                    vertical = DEFAULT_VERTICAL_PADDING,
                ).clickable {
                    click()
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "${student.lastname} ${student.name} ${student.patronymic}",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 20.sp,
        )
    }
}
