package cz.bald.student_tests.ui.test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.Fragment
import cz.bald.student_tests.enum.QuestionType
import cz.bald.student_tests.model.Test
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import cz.bald.studenttests.R
import kotlinx.android.synthetic.main.fragment_test_question_open.view.*
import kotlinx.android.synthetic.main.fragment_test_question_test.view.*
import kotlinx.android.synthetic.main.fragment_test_section.view.*
import java.lang.IllegalArgumentException
import java.util.*

class QuestionFragment(private val test: Test, private val section: Int,
                       private val question: Int, private val navIndex: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true

        val view = when {
            question < 0 -> {
                createSectionView(inflater, container)
            }
            test.sections[section].questions[question].type == QuestionType.TEST -> {
                createTestQuestionView(inflater, container)
            }
            else -> {
                createOpenQuestionView(inflater, container)
            }
        }

        return view
    }

    private fun createSectionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_section, container, false)

        frag.section_text.text = test.sections[section].text

        setupBackButton(frag.section_back_button)
        setupNavButton(frag.section_question_button, false)
        setupNextButton(frag.section_next_button)

        return frag
    }

    private fun createTestQuestionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_question_test, container, false)
        val question = test.sections[section].questions[question]

        frag.question_test_question_number_value.text = question.number.toString() + " / " + test.questionsCount
        frag.question_test_text.text = question.text
        frag.question_test_answers.adapter = ArrayAdapter<String>(this.requireContext(),
            android.R.layout.simple_list_item_1, question.answers)
        if (question.userAnswer.isNotEmpty()) {
            frag.question_test_answers.setSelection(question.userAnswer.toInt())
        }

        frag.question_test_answers.setOnItemClickListener { _, view, i, _ ->
            question.userAnswer = i.toString()
            view.isSelected = true
        }
        setupBackButton(frag.question_test_back_button)
        setupNavButton(frag.question_test_section_button, true)
        setupNextButton(frag.question_test_next_button)

        return frag
    }

    private fun createOpenQuestionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_question_open, container, false)
        val question = test.sections[section].questions[question]

        frag.question_open_question_number_value.text = question.number.toString() + " / " + test.questionsCount
        frag.question_open_text.text = question.text
        frag.question_open_answer.text = Editable.Factory.getInstance().newEditable(question.userAnswer)
        frag.question_open_answer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                question.userAnswer = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        setupBackButton(frag.question_open_back_button)
        setupNavButton(frag.question_open_section_button, true)
        setupNextButton(frag.question_open_next_button)

        return frag
    }

    private fun setupBackButton(button: Button) {
        if (section > 0 || question >= 0) {
            var nextQuestion = if (question < 0) {
                question
            } else {
                question - 1
            }
            var nextSection = section
            if (nextQuestion < -1) {
                nextSection--
                nextQuestion = test.sections[nextSection].questionCount - 1
            }
            button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, nextSection, nextQuestion, Int.MIN_VALUE), false)
            }
        } else {
            button.visibility = View.INVISIBLE
        }
    }

    private fun setupNavButton(button: Button, toSection: Boolean) {
        val fcl = activity as FragmentChangeListener
        if (toSection) {
            button.setOnClickListener {
                fcl.swapFragment(QuestionFragment(test, section, Int.MIN_VALUE, question), false)
            }
        } else {
            if (navIndex >= 0) {
                button.setOnClickListener {
                    fcl.swapFragment(QuestionFragment(test, section, navIndex, Int.MIN_VALUE), false)
                }
            } else {
                button.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupNextButton(button: Button) {
        val fcl = activity as FragmentChangeListener
        if (question < 0 || test.sections[section].questions[question].number < test.questionsCount) {
            var nextQuestion = if (question < 0) {
                0
            } else {
                question + 1
            }
            var nextSection = section
            if (nextQuestion >= test.sections[section].questionCount) {
                nextQuestion = Int.MIN_VALUE
                nextSection++
            }
            button.setOnClickListener {
                fcl.swapFragment(QuestionFragment(test, nextSection, nextQuestion, Int.MIN_VALUE), false)
            }
        } else {
            button.text = getString(R.string.test_finish_button)
            button.setOnClickListener {
                calculateResult()
                fcl.swapFragment(ResultFragment(test.result), true)
            }
        }
    }

    private fun calculateResult() {
        var correct = 0
        var points = 0
        test.sections.forEach {
            it.questions.forEach {
                if (it.userAnswer.equals(it.correctAnswer)) {
                    correct++
                    points += it.points
                }
            }
        }
        test.result.date = Date()
        test.result.correctQuestions = correct
        test.result.points = points
    }
}