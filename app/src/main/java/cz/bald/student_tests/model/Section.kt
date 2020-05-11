package cz.bald.student_tests.model

data class Section(
    val number: Int,
    val text: String,
    val questionCount: Int,
    val maxPoints: Int,
    val questions: List<Question>
)