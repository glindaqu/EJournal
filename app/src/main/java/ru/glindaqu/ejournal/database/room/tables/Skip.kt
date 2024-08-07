package ru.glindaqu.ejournal.database.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skip(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo val pairId: Int = 0,
    @ColumnInfo val date: Long = 0,
    @ColumnInfo val studentId: Int
)
