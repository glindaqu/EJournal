package ru.glindaqu.ejournal.navigation

/**
 * Класс является описанием экрана для функции NavHost
 *
 * @author glindaqu
 * @param[name] Название экрана (текст)
 *
 * @property[home] Роут для домашнего экрана
 * @property[journal] Роут для экрана журнала
 * @property[statistics] Роут для экрана статистики
 * @property[settings] Роут для экрана настроек
 */
data class Route(
    val name: String,
) {
    /**
     * Статическими являются сами роуты, что позволит избежать
     * необходимость их создания для каждого кейса в отдельности
     */
    companion object {
        val home = Route("Home")
        val journal = Route("Journal")
        val statistics = Route("Statistics")
        val settings = Route("Settings")
        val subjects = Route("Subjects")
    }

    /**
     * Функция получения текущего роута
     *
     * @return Возвращает строковый литерал, соответствующий роуту
     */
    fun get(): String = this.name
}
