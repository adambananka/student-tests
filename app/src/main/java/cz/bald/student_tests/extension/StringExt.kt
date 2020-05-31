package cz.bald.student_tests.extension

import cz.bald.student_tests.model.TestSetting
import java.text.SimpleDateFormat
import java.util.*

fun Date.toPrintable(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault())
    return dateFormat.format(this)
}

fun TestSetting.toPrintable(): String {
    return this.type.name + " / " + this.language + " / " + this.subject + " / " + this.year
}