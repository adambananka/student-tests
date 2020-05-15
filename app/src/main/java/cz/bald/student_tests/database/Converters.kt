package cz.bald.student_tests.database

import androidx.room.TypeConverter
import cz.bald.student_tests.model.TestSetting
import cz.bald.student_tests.service.JsonConvertManager
import java.util.Date

class Converters {

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun testSettingToString(value: TestSetting?): String? {
        return if (value == null) null else JsonConvertManager.convertTestSettingToJson(value)
    }

    @TypeConverter
    fun stringToTestSetting(value: String?): TestSetting? {
        return if (value == null) null else JsonConvertManager.convertJsonToTestSetting(value)
    }
}