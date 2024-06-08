package ru.glindaqu.ejournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.glindaqu.ejournal.screens.Home


/**
 * Функция представляет собой базовый компонент навигации приложения
 *
 * @author glindaqu
 */
@Composable
fun NavGraph() {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = Route.home.get()) {
        composable(Route.home.get()) { Home() }
    }
}