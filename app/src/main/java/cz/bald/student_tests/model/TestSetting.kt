package cz.bald.student_tests.model

import cz.bald.student_tests.enum.Language
import cz.bald.student_tests.enum.Subject
import cz.bald.student_tests.enum.TestType
import java.time.Year

data class TestSetting(
    var type: TestType,
    var language: Language,
    var subject: Subject,
    var year: Year
)