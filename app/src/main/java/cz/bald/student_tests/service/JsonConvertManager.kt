package cz.bald.student_tests.service

import com.google.gson.GsonBuilder
import cz.bald.student_tests.enums.CzechSubject
import cz.bald.student_tests.enums.SlovakSubject
import cz.bald.student_tests.enums.Subject
import cz.bald.student_tests.model.Test
import cz.bald.student_tests.model.TestSetting
import java.io.Reader

class JsonConvertManager {

    companion object {
        private val converter = GsonBuilder()
            .registerTypeAdapter(Subject::class.java, SubjectInterfaceAdapter())
            .registerTypeAdapter(SlovakSubject::class.java, SubjectInterfaceAdapter())
            .registerTypeAdapter(CzechSubject::class.java, SubjectInterfaceAdapter())
            .create()

        fun convertJsonToTest(jsonReader: Reader): Test {
            val result = convertJsonToTest(jsonReader.readText())
            jsonReader.close()
            return result
        }

        fun convertTestToJson(test: Test): String {
            return converter.toJson(test)
        }

        fun convertJsonToTest(json: String): Test {
            return converter.fromJson(json, Test::class.java)
        }

        fun convertTestSettingToJson(setting: TestSetting): String {
            return converter.toJson(setting)
        }

        fun convertJsonToTestSetting(json: String): TestSetting {
            return converter.fromJson(json, TestSetting::class.java)
        }
    }
}