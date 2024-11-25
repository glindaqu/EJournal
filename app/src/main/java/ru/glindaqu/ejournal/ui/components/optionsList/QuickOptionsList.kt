package ru.glindaqu.ejournal.ui.components.optionsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.glindaqu.ejournal.R
import ru.glindaqu.ejournal.navigation.Route

@Suppress("ktlint:standard:function-naming")
@Composable
fun QuickOptionsList(navController: NavHostController) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuickOption(
            title = stringResource(id = R.string.quick_option_journal),
            onClick = { navController.navigate(Route.journal.get()) },
        )
        QuickOption(
            title = stringResource(id = R.string.quick_option_omissions),
            onClick = { navController.navigate(Route.statistics.get()) },
        )
//        QuickOption(title = stringResource(id = R.string.quick_option_export_to)) { }
    }
}
