package cz.bald.student_tests.model

import android.os.Parcelable
import cz.bald.student_tests.enums.Language
import cz.bald.student_tests.enums.Subject
import cz.bald.student_tests.enums.TestType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TestSetting(
    var type: TestType,
    var language: Language,
    var subject: Subject,
    var year: Int
) : Parcelable