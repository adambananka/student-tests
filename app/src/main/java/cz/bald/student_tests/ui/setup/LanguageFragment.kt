package cz.bald.student_tests.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.bald.student_tests.enums.CzechSubject
import cz.bald.student_tests.enums.Language
import cz.bald.student_tests.enums.SlovakSubject
import cz.bald.student_tests.enums.Subject
import cz.bald.student_tests.model.TestSetting
import cz.bald.studenttests.R
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import kotlinx.android.synthetic.main.fragment_setup_language.view.*

class LanguageFragment(private val testSetting: TestSetting) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_setup_language, container, false)

        view.setup_language_slovak_button.setOnClickListener {
            saveAndContinue(Language.SLOVAK, SlovakSubject.SLOVAK)
        }
        view.setup_language_czech_button.setOnClickListener {
            saveAndContinue(Language.CZECH, CzechSubject.CZECH)
        }

        return view
    }

    private fun saveAndContinue(lang: Language, subject: Subject) {
        val fcl = activity as FragmentChangeListener
        testSetting.language = lang
        testSetting.subject = subject
        fcl.swapFragment(SubjectFragment(testSetting), true)
    }
}