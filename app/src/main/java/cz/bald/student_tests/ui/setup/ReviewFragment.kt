package cz.bald.student_tests.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.bald.studenttests.R
import cz.bald.student_tests.ui.listener.SetupListener
import kotlinx.android.synthetic.main.fragment_setup_review.view.*

class ReviewFragment(private val testSetting: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_setup_review, container, false)

        view.review_selection_text_view.text = testSetting

        view.review_confirm_button.setOnClickListener{
            val fcl = activity as SetupListener
            fcl.finishSetup(testSetting)
        }

        return view
    }
}