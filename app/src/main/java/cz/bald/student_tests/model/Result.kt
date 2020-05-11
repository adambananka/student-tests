package cz.bald.student_tests.model

import java.util.Date

data class Result(
    val test: String,
    var date: Date,
    val questions: Int,
    var correctQuestions: Int,
    var points: Int,
    val maxPoints: Int
)