package cz.bald.student_tests.enum

interface Subject {

    fun getAllSubjects(): Array<String>

    fun getSubjectName(): String

    fun getYearsOfSubject(): Array<Int>
}