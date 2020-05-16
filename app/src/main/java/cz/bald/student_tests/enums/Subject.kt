package cz.bald.student_tests.enums

import android.os.Parcelable

interface Subject : Parcelable {

    fun getAllSubjects(): List<String>

    fun getSubjectName(): String

    fun of(text: String): Subject

    fun getYearsOfSubject(): List<Int>
}