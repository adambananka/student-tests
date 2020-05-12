package cz.bald.student_tests.ui.setup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cz.bald.student_tests.enum.CzechSubject
import cz.bald.student_tests.enum.Language
import cz.bald.student_tests.enum.TestType
import cz.bald.student_tests.model.TestSetting
import cz.bald.studenttests.R
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import cz.bald.student_tests.ui.listener.SetupListener
import cz.bald.student_tests.ui.test.TestActivity

class SetupActivity : AppCompatActivity(), FragmentChangeListener, SetupListener {

    companion object {
        const val ARG_SETUP = "arg_setup"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        val setting = TestSetting(TestType.MATURITA, Language.SLOVAK, CzechSubject.CZECH, 0)
        val fragment = StartFragment(setting)
        swapFragment(fragment, false)
    }

    override fun swapFragment(newFragment: Fragment, stack: Boolean) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.setup_fragment_container, newFragment)
            .addToBackStack(newFragment.toString())
            .commit()
    }

    override fun finishSetup(setting: TestSetting) {
        val intent = Intent(this, TestActivity::class.java)
        intent.putExtra(ARG_SETUP, setting)
        startActivity(intent)
    }
}