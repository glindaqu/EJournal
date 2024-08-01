package ru.glindaqu.ejournal.ui.components.bottomBar


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.glindaqu.ejournal.DEFAULT_CARD_ELEVATION
import ru.glindaqu.ejournal.dataModels.BottomBarItemData
import ru.glindaqu.ejournal.ui.components.bottomSlider.BottomSlider

@Composable
fun BottomBar(destinations: List<BottomBarItemData>, controller: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BottomSlider()
        NavigationBar(
            tonalElevation = DEFAULT_CARD_ELEVATION,
            containerColor = MaterialTheme.colorScheme.onBackground
        ) {
            destinations.forEach { el ->
                BottomBarItemView(
                    itemData = el,
                    selected = false,
                    controller = controller
                )
            }
        }
    }
}