package cz.bald.student_tests.enum

enum class Language {
    SLOVAK {
        override fun getSubjects(): List<String> {
            return SlovakSubject.SLOVAK.getAllSubjects()
        }
    },
    CZECH {
        override fun getSubjects(): List<String> {
            return CzechSubject.CZECH.getAllSubjects()
        }
    };

    abstract fun getSubjects(): List<String>
}