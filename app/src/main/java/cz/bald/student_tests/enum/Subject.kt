package cz.bald.student_tests.enum

import android.os.Parcelable

interface Subject: Parcelable {

    fun getAllSubjects(): Array<String>

    fun getSubjectName(): String

    fun of(text: String): Subject

    fun getYearsOfSubject(): Array<Int>
}