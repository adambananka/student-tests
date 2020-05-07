package cz.bald.student_tests.ui.listener

import androidx.fragment.app.Fragment

interface FragmentChangeListener {

    fun swapFragment(newFragment: Fragment, stack: Boolean)
}