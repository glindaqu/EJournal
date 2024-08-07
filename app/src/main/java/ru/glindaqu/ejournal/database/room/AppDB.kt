package ru.glindaqu.ejournal.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.Skip
import ru.glindaqu.ejournal.database.room.tables.User

@Database(
    entities = [Mark::class, Pair::class, Skip::class, User::class], version = 1
)
abstract class AppDB : RoomDatabase() {
    companion object {
        fun getDatabase(context: Context): AppDB {
            return Room.databaseBuilder(
                context = context.applicationContext,
                klass = AppDB::class.java,
                name = "eJournalBase.db"
            ).build()
        }
    }
}