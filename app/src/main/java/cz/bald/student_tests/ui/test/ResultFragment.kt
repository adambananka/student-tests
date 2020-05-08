package cz.bald.student_tests.ui.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.bald.student_tests.ui.setup.SetupActivity
import cz.bald.studenttests.R
import kotlinx.android.synthetic.main.fragment_test_result.view.*

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_test_result, container, false)

        view.result_questions_value.text = "ab / cd"
        view.result_points_value.text = "ef / gh"
        view.result_percentage_value.text = "xy%"

        view.result_finish_button.setOnClickListener {
            startActivity(Intent(activity, SetupActivity::class.java))
        }

        return view
    }
}