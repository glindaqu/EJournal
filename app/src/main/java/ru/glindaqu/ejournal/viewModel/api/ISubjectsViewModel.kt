package ru.glindaqu.ejournal.viewModel.api

import kotlinx.coroutines.flow.Flow

interface ISubjectsViewModel {
    fun getAllSubject(): Flow<List<String>>
}
