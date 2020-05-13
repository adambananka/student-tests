package cz.bald.student_tests.model

import cz.bald.student_tests.enum.QuestionType

data class Question(
    val number: Int,
    val points: Int,
    val type: QuestionType,
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
    var userAnswer: String
)