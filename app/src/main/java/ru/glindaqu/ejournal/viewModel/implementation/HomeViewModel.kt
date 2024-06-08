package ru.glindaqu.ejournal.viewModel.implementation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.glindaqu.ejournal.viewModel.api.IHomeViewModel

/**
 * viewModel для домашнего экрана пользователя. Наследует интерфейс [IHomeViewModel] и класс
 * [AndroidViewModel]
 *
 * @author glindaqu
 *
 * @constructor Принимает [application] (экземпляр класса [Application]), для вызова конструктора
 * класса [AndroidViewModel]
 *
 * @param[application] Экземпляр класса [Application]
 */
class HomeViewModel(application: Application) : IHomeViewModel, AndroidViewModel(application) {
}