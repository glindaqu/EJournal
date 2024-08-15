package ru.glindaqu.ejournal.screens.students

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.viewModel.implementation.StudentsViewModel

@Suppress("ktlint:standard:function-naming")
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddStudent(viewModel: StudentsViewModel) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var lastname by remember { mutableStateOf(TextFieldValue("")) }
    var patronymic by remember { mutableStateOf(TextFieldValue("")) }
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { viewModel.uiState.value = StudentsUIState.VIEW }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp),
                )
            }
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.insert(
                        name = name.text,
                        lastname = lastname.text,
                        patronymic = patronymic.text,
                    )
                    viewModel.uiState.value = StudentsUIState.VIEW
                },
                modifier =
                    Modifier
                        .padding(end = DEFAULT_HORIZONTAL_PADDING / 2)
                        .padding(10.dp)
                        .shadow(
                            elevation = 7.dp,
                            shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                            clip = true,
                            ambientColor = Color.Black,
                            spotColor = Color.Black,
                        ).clip(RoundedCornerShape(DEFAULT_CORNER_CLIP)),
                shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(text = "Сохранить", fontSize = 16.sp)
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                    )
                }
            }
        }
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .padding(it)
                    .padding(top = 10.dp)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                StudentTextField(
                    value = lastname,
                    placeholderText = "Фамилия студента",
                ) { value -> lastname = value }
                StudentTextField(value = name, placeholderText = "Имя студента") { value ->
                    name = value
                }
                StudentTextField(
                    value = patronymic,
                    placeholderText = "Отчество студента",
                ) { value -> patronymic = value }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
internal fun StudentTextField(
    value: TextFieldValue,
    placeholderText: String,
    onValueChange: (TextFieldValue) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholderText,
                color = Color.Black,
                modifier = Modifier.alpha(0.2f),
            )
        },
        modifier =
            Modifier
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .background(MaterialTheme.colorScheme.onBackground),
        colors =
            TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
            ),
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
    )
}
