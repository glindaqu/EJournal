package ru.glindaqu.ejournal.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.glindaqu.ejournal.database.room.dao.MarkDao
import ru.glindaqu.ejournal.database.room.dao.PairDao
import ru.glindaqu.ejournal.database.room.dao.PeopleDao
import ru.glindaqu.ejournal.database.room.dao.SkipDao
import ru.glindaqu.ejournal.database.room.tables.Mark
import ru.glindaqu.ejournal.database.room.tables.Pair
import ru.glindaqu.ejournal.database.room.tables.People
import ru.glindaqu.ejournal.database.room.tables.Skip

@Database(
    entities = [Mark::class, Pair::class, Skip::class, People::class],
    version = 1,
)
abstract class AppDB : RoomDatabase() {
    abstract fun getPairDao(): PairDao

    abstract fun getPeopleDao(): PeopleDao

    abstract fun getMarkDao(): MarkDao

    abstract fun getSkipDao(): SkipDao

    companion object {
        fun getDatabase(context: Context): AppDB =
            Room
                .databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDB::class.java,
                    name = "eJournalBase.db",
                ).addCallback(onDatabaseCreate)
                .build()

        private var onDatabaseCreate: Callback =
            object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        "INSERT INTO Pair(title, teacherId) VALUES " +
                            "('Математика', 1)," +
                            "('Основы философии', 1)," +
                            "('ОАИП', 1)," +
                            "('Физика', 1)," +
                            "('РПС', 1)," +
                            "('РМП', 1)," +
                            "('Разработка веб-приложений', 1)," +
                            "('Теории вероятностей', 1)",
                    )
                    db.execSQL(
                        "INSERT INTO People(name, lastname, patronymic, role) VALUES " +
                            "('Дарья', 'Воробьева', 'Алексеевна', 'student')," +
                            "('Ярослав', 'Красовский', 'Максимович', 'student')," +
                            "('Анастасия', 'Литвинцева', 'Павловна', 'student')," +
                            "('Яков', 'Васин', 'Валерьевич', 'student')," +
                            "('Андрей', 'Бетенеков', 'Андреевич', 'student')," +
                            "('Николай', 'Лубочников', 'Владимирович', 'student')," +
                            "('Серафим', 'Ушаков', '', 'student')," +
                            "('Полина', 'Решетова', '', 'student')," +
                            "('Алексей', 'Никулин', '', 'student')," +
                            "('Андрей', 'Соснов', '', 'student')," +
                            "('Захар', 'Кремер', '', 'student')," +
                            "('Даниил', 'Гаврильчик', '', 'student')," +
                            "('Антон', 'Пурей', '', 'student')",
                    )
                }
            }
    }
}
