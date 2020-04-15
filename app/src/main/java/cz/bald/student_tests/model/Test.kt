package cz.bald.student_tests.model

import cz.bald.student_tests.model.enums.Language
import cz.bald.student_tests.model.enums.Subject
import java.time.Year

data class Test(
    val id: Long,
    val language: Language,
    val subject: Subject,
    val year: Year,
    val result: Result,
    val sections: List<Section>
)
