package ru.glindaqu.ejournal.navigation

/**
 * Класс является описанием экрана для функции NavHost
 *
 * @author glindaqu
 * @param[name] Название экрана (текст)
 */
data class Route(val name: String) {
    /**
     * Статическими являются сами роуты, что позволит избежать
     * необходимость их создания для каждого кейса в отдельности
     */
    companion object {
        val home = Route("Home")
    }

    /**
     * Функция получения текущего роута
     *
     * @return Возвращает строковый литерал, соответствующий роуту
     */
    fun get(): String {
        return this.name
    }
}
