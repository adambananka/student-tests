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

        view.result_questions_value.text = result.correctQuestions.toString() + " / " + result.questions
        view.result_points_value.text = result.points.toString() + " / " + result.maxPoints
        view.result_percentage_value.text = (100.0 * result.points / result.maxPoints).toString() + "%"

        view.result_finish_button.setOnClickListener {
            startActivity(Intent(activity, SetupActivity::class.java))
        }

        return view
    }
}