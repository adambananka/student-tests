package cz.bald.student_tests.model

import java.time.LocalDate

data class Result(
    val test: String,
    val date: LocalDate,
    val questions: Int,
    val correctQuestions: Int,
    val points: Int,
    val maxPoints: Int
)