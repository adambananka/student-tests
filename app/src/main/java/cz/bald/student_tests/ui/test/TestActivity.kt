package cz.bald.student_tests.ui.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import cz.bald.student_tests.ui.setup.SetupActivity
import cz.bald.studenttests.R

class TestActivity : AppCompatActivity(), FragmentChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val setting = intent.getStringExtra(SetupActivity.ARG_SETUP)
        Toast.makeText(this, setting, setting.length).show()

        val fragment = QuestionFragment(listOf(1, 2, 2, 3, 2, 1, 2, 3, 2), 0, Int.MIN_VALUE,
            Int.MIN_VALUE)
        swapFragment(fragment, true)
    }

    override fun swapFragment(newFragment: Fragment, stack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newFragment)
        if (stack) {
            transaction.addToBackStack(newFragment.toString())
        }
        transaction.commit()
    }
}