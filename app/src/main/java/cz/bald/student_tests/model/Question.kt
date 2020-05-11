package cz.bald.student_tests.model

data class Question(
    val number: Int,
    val points: Int,
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
    var userAnswer: String
)