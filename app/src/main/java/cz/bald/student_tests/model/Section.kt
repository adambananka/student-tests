package cz.bald.student_tests.model

data class Section(
    val number: Int,
    val text: String,
    val questions: List<Question>
)