package ru.glindaqu.ejournal.modules.simpleCalendar

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Формат для получения месяца (название целиком, на английском) и дня
 *
 * @author glindaqu
 */
@SuppressLint("SimpleDateFormat")
val monthAndDay = SimpleDateFormat("MMMM, dd", Locale.US)

/**
 * Формат для получения дня в месяце
 *
 * @author glindaqu
 */
@SuppressLint("SimpleDateFormat")
val dayOnly = SimpleDateFormat("d")

/**
 * Формат для получения дня в неделе
 *
 * @author glindaqu
 */
@SuppressLint("SimpleDateFormat")
val dayOfWeek = SimpleDateFormat("u")

/**
 * Формат для получения часа и минуты с маркером (am/pm)
 *
 * @author glindaqu
 */
@SuppressLint("SimpleDateFormat")
val hoursAndMinutes = SimpleDateFormat("hh:mm a")