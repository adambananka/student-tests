package cz.bald.student_tests.enum

import kotlinx.android.parcel.Parcelize

@Parcelize
enum class CzechSubject(private val firstYear: Int, private val lastYear: Int): Subject {
    CZECH(2016, 2019),
    ENGLISH(2016, 2019),
    MATH(2016, 2019);

    override fun getAllSubjects(): Array<String> {
        return values().map { subject -> subject.name }.toTypedArray()
    }

    override fun getSubjectName(): String {
        return this.name
    }

    override fun of(text: String): Subject {
        return valueOf(text)
    }

    override fun getYearsOfSubject(): Array<Int> {
        return (firstYear..lastYear).toList().toTypedArray()
    }
}