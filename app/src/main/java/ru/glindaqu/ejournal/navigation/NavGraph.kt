package ru.glindaqu.ejournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.glindaqu.ejournal.screens.Home
import ru.glindaqu.ejournal.screens.Journal


/**
 * Функция представляет собой базовый компонент навигации приложения
 *
 * @author glindaqu
 */
@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Route.journal.get()
    ) {
        composable(Route.home.get()) { Home() }
        composable(Route.journal.get()) { Journal() }
        composable(Route.statistics.get()) { Home() }
        composable(Route.settings.get()) { Home() }
    }
}