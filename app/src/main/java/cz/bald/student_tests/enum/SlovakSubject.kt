package cz.bald.student_tests.enum

enum class SlovakSubject(private val firstYear: Int, private val lastYear: Int): Subject {
    SLOVAK(2009, 2019),
    ENGLISH(2009, 2019),
    MATH(2009, 2019);

    override fun getAllSubjects(): Array<String> {
        return values().map { subject -> subject.name }.toTypedArray()
    }

    override fun getSubjectName(): String {
        return this.name
    }

    override fun getYearsOfSubject(): Array<Int> {
        return (firstYear..lastYear).toList().toTypedArray()
    }
}