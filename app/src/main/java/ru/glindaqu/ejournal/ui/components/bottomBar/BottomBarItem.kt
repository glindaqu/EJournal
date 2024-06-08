package ru.glindaqu.ejournal.ui.components.bottomBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.glindaqu.ejournal.dataModels.BottomBarItemData

@Composable
fun RowScope.BottomBarItemView(itemData: BottomBarItemData, selected: Boolean) {
    NavigationBarItem(
        selected = selected,
        onClick = {},
        icon = {
            Icon(
                painter = painterResource(id = itemData.icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    )
}