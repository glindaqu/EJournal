package ru.glindaqu.ejournal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.glindaqu.ejournal.screens.Home
import ru.glindaqu.ejournal.screens.detail.Detail
import ru.glindaqu.ejournal.screens.journal.Journal
import ru.glindaqu.ejournal.screens.settings.Settings
import ru.glindaqu.ejournal.screens.statistics.Statistics
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
        startDestination = Route.home.get(),
    ) {
        composable(Route.home.get()) {
            Home(navController = navHostController)
            onDestinationChanged(Route.home.get())
        }
        composable(Route.journal.get()) {
            Journal(navHostController = navHostController)
            onDestinationChanged(Route.journal.get())
        }
        composable(Route.statistics.get()) {
            Statistics()
            onDestinationChanged(Route.statistics.get())
        }
        composable(Route.settings.get()) {
            Settings()
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
        composable(
            route = Route.detail.get() + "/{id}",
            arguments = listOf(navArgument("id") { NavType.StringType }),
        ) {
            Detail(studentId = it.arguments?.getString("id")!!.toInt(), onClose = {
                navHostController.navigateUp()
            })
        }
    }
}
