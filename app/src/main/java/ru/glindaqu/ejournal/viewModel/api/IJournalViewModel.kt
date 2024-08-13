package ru.glindaqu.ejournal.viewModel.api

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface IJournalViewModel {
    fun attachContext(context: Context)

    fun getSubjects(): Flow<List<String>>
}
