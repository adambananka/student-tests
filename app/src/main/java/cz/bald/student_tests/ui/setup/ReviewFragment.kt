package cz.bald.student_tests.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.bald.student_tests.model.TestSetting
import cz.bald.studenttests.R
import cz.bald.student_tests.ui.listener.SetupListener
import kotlinx.android.synthetic.main.fragment_setup_review.view.*

class ReviewFragment(private val testSetting: TestSetting) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_setup_review, container, false)

        view.setup_review_selection_label.text = getString(R.string.setup_review_selection_value,
            testSetting.type.name, testSetting.language.name, testSetting.subject.getSubjectName(),
            testSetting.year)
        view.setup_review_confirm_button.setOnClickListener{
            val fcl = activity as SetupListener
            fcl.finishSetup(testSetting)
        }

        return view
    }
}