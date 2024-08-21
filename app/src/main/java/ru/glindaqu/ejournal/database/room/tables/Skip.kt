package ru.glindaqu.ejournal.database.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skip(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,
    @ColumnInfo val pairId: Int = 0,
    @ColumnInfo val date: Long = 0,
    @ColumnInfo val studentId: Int,
    @ColumnInfo val reasonType: Int,
) {
    override fun toString(): String =
        when (this.reasonType) {
            0 -> "н/б"
            1 -> "ув"
            else -> ""
        }
}
