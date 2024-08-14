package ru.glindaqu.ejournal.ui.components.bottomBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.glindaqu.ejournal.dataModels.BottomBarItemData

@Suppress("ktlint:standard:function-naming")
@Composable
fun RowScope.BottomBarItemView(
    itemData: BottomBarItemData,
    selected: Boolean,
    controller: NavHostController,
) {
    NavigationBarItem(
        selected = selected,
        onClick = { controller.navigate(itemData.destination) },
        icon = {
            Icon(
                painter = painterResource(id = itemData.icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
            )
        },
    )
}
