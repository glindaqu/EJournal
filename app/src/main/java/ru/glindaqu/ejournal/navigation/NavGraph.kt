package ru.glindaqu.ejournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.glindaqu.ejournal.screens.Home
import ru.glindaqu.ejournal.screens.journal.Journal
import ru.glindaqu.ejournal.screens.students.EditStudent
import ru.glindaqu.ejournal.screens.students.Students
import ru.glindaqu.ejournal.screens.subjects.EditSubject
import ru.glindaqu.ejournal.screens.subjects.Subjects

@Suppress("ktlint:standard:function-naming")
/**
 * Функция представляет собой базовый компонент навигации приложения
 *
 * @author glindaqu
 */
@Composable
fun NavGraph(
    navHostController: NavHostController,
    onDestinationChanged: (String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.journal.get(),
    ) {
        composable(Route.home.get()) {
            Home()
            onDestinationChanged(Route.home.get())
        }
        composable(Route.journal.get()) {
            Journal()
            onDestinationChanged(Route.journal.get())
        }
        composable(Route.statistics.get()) {
            Home()
            onDestinationChanged(Route.statistics.get())
        }
        composable(Route.settings.get()) {
            Home()
            onDestinationChanged(Route.settings.get())
        }
        composable(Route.subjects.get()) {
            Subjects(navHostController = navHostController)
        }
        composable(Route.students.get()) {
            Students(navHostController = navHostController)
        }
        composable(
            route = Route.editSubject.get() + "/{title}",
            arguments = listOf(navArgument("title") { NavType.StringType }),
        ) {
            EditSubject(
                title = it.arguments?.getString("title")!!,
                popUp = { navHostController.navigate(Route.subjects.get()) },
            )
        }
        composable(
            route = Route.editStudent.get() + "/{id}",
            arguments = listOf(navArgument("id") { NavType.StringType }),
        ) {
            EditStudent(
                id = it.arguments?.getString("id")!!.toInt(),
                popUp = { navHostController.navigate(Route.students.get()) },
            )
        }
    }
}
