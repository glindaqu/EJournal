@file:Suppress("ktlint:standard:filename")

package ru.glindaqu.ejournal.navigation

import ru.glindaqu.ejournal.R
import ru.glindaqu.ejournal.dataModels.BottomBarItemData

interface IRoutes {
    val destinations: List<BottomBarItemData>
        get() =
            listOf(
                BottomBarItemData(R.string.dest_home, R.drawable.home, "home"),
                BottomBarItemData(R.string.dest_journal, R.drawable.journal, "journal"),
                BottomBarItemData(R.string.dest_omissions, R.drawable.omissions, "omissions"),
                BottomBarItemData(R.string.dest_settings, R.drawable.settings, "settings"),
            )
}
