package ru.glindaqu.ejournal.screens.subjects

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
import ru.glindaqu.ejournal.viewModel.implementation.SubjectsViewModel

@Suppress("ktlint:standard:function-naming")
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddSubject(viewModel: SubjectsViewModel) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
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
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.insertSubject(title.text)
                    viewModel.uiState.value = SubjectsUIState.VIEW
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
            modifier = Modifier.padding(it).padding(top = 10.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = title,
                onValueChange = { value -> title = value },
                placeholder = {
                    Text(
                        text = "Название премета",
                        color = Color.Black,
                        modifier = Modifier.alpha(0.2f),
                    )
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                colors =
                    TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                    ),
                maxLines = 1,
            )
        }
    }
}
