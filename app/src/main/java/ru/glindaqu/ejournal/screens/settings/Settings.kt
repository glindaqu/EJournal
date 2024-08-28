package ru.glindaqu.ejournal.screens.settings

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import ru.glindaqu.ejournal.DEFAULT_CARD_ELEVATION
import ru.glindaqu.ejournal.DEFAULT_CORNER_CLIP
import ru.glindaqu.ejournal.DEFAULT_HORIZONTAL_PADDING
import ru.glindaqu.ejournal.DEFAULT_VERTICAL_PADDING
import ru.glindaqu.ejournal.viewModel.implementation.SettingsViewModel

@Suppress("ktlint:standard:function-naming")
@Composable
fun Settings() {
    val viewModel =
        ViewModelProvider(LocalContext.current as ComponentActivity)[SettingsViewModel::class.java]
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(vertical = DEFAULT_VERTICAL_PADDING),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SettingsItem(label = "Удалить все пропуски", onClick = viewModel::deleteAllSkips)
        SettingsItem(label = "Удалить все оценки", onClick = viewModel::deleteAllMarks)
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
private fun SettingsItem(
    label: String,
    onClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
        elevation = CardDefaults.cardElevation(DEFAULT_CARD_ELEVATION),
        shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
        modifier =
            Modifier
                .clip(RoundedCornerShape(DEFAULT_CORNER_CLIP))
                .fillMaxWidth()
                .padding(horizontal = DEFAULT_HORIZONTAL_PADDING)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(DEFAULT_CORNER_CLIP),
                    clip = true,
                    ambientColor = Color.Black,
                    spotColor = Color.Black,
                ).clip(
                    RoundedCornerShape(
                        DEFAULT_CORNER_CLIP,
                    ),
                ).clickable {
                    onClick()
                },
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                modifier =
                    Modifier
                        .wrapContentSize()
                        .padding(
                            horizontal = DEFAULT_HORIZONTAL_PADDING,
                            vertical = DEFAULT_VERTICAL_PADDING,
                        ),
                color = Color.Black,
            )
        }
    }
}
