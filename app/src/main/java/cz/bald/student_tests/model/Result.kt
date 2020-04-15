package cz.bald.student_tests.model

import java.time.LocalDate

data class Result(
    val date: LocalDate,
    val user: User,
    val points: Int,
    val maxPoints: Int
)