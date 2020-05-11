package cz.bald.student_tests.ui.listener

import cz.bald.student_tests.model.TestSetting

interface SetupListener {

    fun finishSetup(setting: TestSetting)
}