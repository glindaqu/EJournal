package ru.glindaqu.ejournal.database.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val name: String,
    @ColumnInfo val lastname: String,
    @ColumnInfo val patronymic: String
)
