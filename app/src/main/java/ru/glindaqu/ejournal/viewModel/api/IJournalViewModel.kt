package ru.glindaqu.ejournal.viewModel.api

import kotlinx.coroutines.flow.Flow
import ru.glindaqu.ejournal.database.room.tables.Pair

interface IJournalViewModel {
    fun getSubjects(): Flow<List<Pair>>
}
