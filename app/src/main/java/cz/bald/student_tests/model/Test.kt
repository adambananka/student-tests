package cz.bald.student_tests.model

data class Test(
    val setting: TestSetting,
    val sections: List<Section>,
    val questionsCount: Int,
    val maxPoints: Int,
    val timeAvailable: Int,
    var result: Result
)
