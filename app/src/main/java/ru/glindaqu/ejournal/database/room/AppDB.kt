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
                            "('Технология разработки программного обеспечения', 1)," +
                            "('Системное программирование', 1)," +
                            "('Инструментальные средства разработки программного обеспечения', 1)," +
                            "('Английский язык', 1)," +
                            "('Физическая культура', 1)," +
                            "('Теория разработки и защиты баз данных', 1)," +
                            "('Экономика отрасли', 1)," +
                            "('Менеджмент', 1)," +
                            "('Обеспечение качества функционирования компьютерных систем', 1)," +
                            "('Финансовая грамотность', 1)," +
                            "('Проектирование, разработка и оптимизация веб-приложений', 1)",
                    )
                    db.execSQL(
                        "INSERT INTO People(lastname, name, patronymic, role) VALUES " +
                            "('Баданов', 'Роман', 'Викторович', 'student')," +
                            "('Бетенеков', 'Андрей', 'Андреевич', 'student')," +
                            "('Брит', 'Матвей', 'Сергеевич', 'student')," +
                            "('Васин', 'Яков', 'Валерьевич', 'student')," +
                            "('Воробьева', 'Дарья', 'Алексеевна', 'student')," +
                            "('Гаврильчик', 'Данил', 'Алексеевич', 'student')," +
                            "('Исупов', 'Владислав', 'Владимирович', 'student')," +
                            "('Красовский', 'Ярослав', 'Максимович', 'student')," +
                            "('Кремер', 'Захар', 'Сергеевич', 'student')," +
                            "('Литвинцева', 'Анастасия', 'Павловна', 'student')," +
                            "('Лубочников', 'Николай', 'Владимирович', 'student')," +
                            "('Макаров', 'Кирилл', 'Константинович', 'student')," +
                            "('Мартыненко', 'Вадим', 'Вячеславович', 'student')," +
                            "('Никулин', 'Алексей', 'Сергеевич', 'student')," +
                            "('Пантелеев', 'Александр', 'Максимович', 'student')," +
                            "('Поляков', 'Владимир', 'Денисович', 'student')," +
                            "('Попинако', 'Андрей', 'Витальевич', 'student')," +
                            "('Пурей', 'Антон', 'Андреевич', 'student')," +
                            "('Решетова', 'Полина', 'Александровна', 'student')," +
                            "('Соснов', 'Андрей', 'Игоревич', 'student')," +
                            "('Тюнин', 'Вячеслав', 'Евгеньевич', 'student')," +
                            "('Ушаков', 'Серафим', 'Вадимович', 'student')," +
                            "('Шурутов', 'Александр', 'Сергеевич', 'student')",
                    )
                }
            }
    }
}
