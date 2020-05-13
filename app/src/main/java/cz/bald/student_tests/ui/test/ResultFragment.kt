package cz.bald.student_tests.ui.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cz.bald.student_tests.model.Result
import cz.bald.student_tests.ui.setup.SetupActivity
import cz.bald.studenttests.R
import kotlinx.android.synthetic.main.fragment_test_result.view.*

class ResultFragment(private val result: Result) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_test_result, container, false)

        view.test_result_questions_value.text = getString(R.string.test_result_questions_value,
            result.correctQuestions, result.questions)
        view.test_result_points_value.text = getString(R.string.test_result_points_value, result.points,
            result.maxPoints)
        val pct = 100.0 * result.points / result.maxPoints
        view.test_result_percentage_value.text = getString(R.string.test_result_percentage_value, pct)

        view.test_result_finish_button.setOnClickListener {
            startActivity(Intent(activity, SetupActivity::class.java))
        }

        return view
    }
}