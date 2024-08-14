package ru.glindaqu.ejournal.viewModel.api

import kotlinx.coroutines.flow.Flow

interface ISubjectsViewModel {
    fun getAllSubject(): Flow<List<String>>

    fun deleteSubject(title: String)

    fun update(
        old: String,
        new: String,
    )
}
