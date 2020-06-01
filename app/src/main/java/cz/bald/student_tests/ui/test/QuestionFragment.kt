package cz.bald.student_tests.ui.test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import cz.bald.student_tests.database.StudentTestsDatabase
import cz.bald.student_tests.enums.QuestionType
import cz.bald.student_tests.model.Question
import cz.bald.student_tests.model.Result
import cz.bald.student_tests.model.Test
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import cz.bald.studenttests.R
import kotlinx.android.synthetic.main.fragment_test_question_open.view.*
import kotlinx.android.synthetic.main.fragment_test_question_test.view.*
import kotlinx.android.synthetic.main.fragment_test_section.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class QuestionFragment(private val test: Test, private val section: Int,
                       private val question: Int, private val navIndex: Int) : Fragment() {

    companion object {
        const val NO_QUESTION = -1
    }

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

        frag.test_section_title.text = getString(R.string.test_section_title,
            test.sections[section].number)
        frag.test_section_text.text = test.sections[section].text
        setupBackButton(frag.test_section_back_button)
        setupNavButton(frag.test_section_question_button, false)
        setupNextButton(frag.test_section_next_button)

        return frag
    }

    private fun createTestQuestionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_question_test, container, false)
        val question = test.sections[section].questions[question]

        frag.test_question_test_section_label.text = getString(R.string.test_section_title,
            test.sections[section].number)
        frag.test_question_test_question_label.text = getString(
            R.string.test_question_number, question.number, test.questionsCount)
        frag.test_question_test_text.text = question.text
        frag.test_question_test_answers.adapter = ArrayAdapter<String>(this.requireContext(),
            android.R.layout.simple_list_item_activated_1, question.answers)
        if (question.userAnswer.isNotEmpty()) {
            frag.test_question_test_answers.setItemChecked(question.userAnswer.toInt(), true)
        }
        frag.test_question_test_answers.setOnItemClickListener { _, view, i, _ ->
            question.userAnswer = i.toString()
            view.isSelected = true
        }
        setupBackButton(frag.test_question_test_back_button)
        setupNavButton(frag.test_question_test_section_button, true)
        setupNextButton(frag.test_question_test_next_button)

        return frag
    }

    private fun createOpenQuestionView(inflater: LayoutInflater, container: ViewGroup?): View {
        val frag = inflater.inflate(R.layout.fragment_test_question_open, container, false)
        val question = test.sections[section].questions[question]

        frag.test_question_open_section_label.text = getString(R.string.test_section_title,
            test.sections[section].number)
        frag.test_question_open_question_label.text = getString(
            R.string.test_question_number, question.number, test.questionsCount)
        frag.test_question_open_text.text = question.text
        frag.test_question_open_answer.text = Editable.Factory.getInstance()
            .newEditable(question.userAnswer)
        frag.test_question_open_answer.addTextChangedListener(OpenQuestionAnswerChangeListener(question))
        setupBackButton(frag.test_question_open_back_button)
        setupNavButton(frag.test_question_open_section_button, true)
        setupNextButton(frag.test_question_open_next_button)

        return frag
    }

    private fun setupBackButton(button: Button) {
        if (section > 0 || question >= 0) {
            var nextQuestion = question - 1
            var nextSection = section
            if (nextQuestion < -1) {
                nextSection--
                nextQuestion = test.sections[nextSection].questionCount - 1
            }
            button.setOnClickListener {
                val fcl = activity as FragmentChangeListener
                fcl.swapFragment(QuestionFragment(test, nextSection, nextQuestion, NO_QUESTION), false)
            }
        } else {
            button.visibility = View.INVISIBLE
        }
    }

    private fun setupNavButton(button: Button, toSection: Boolean) {
        val fcl = activity as FragmentChangeListener
        if (toSection) {
            button.setOnClickListener {
                fcl.swapFragment(QuestionFragment(test, section, NO_QUESTION, question), false)
            }
        } else {
            if (navIndex >= 0) {
                button.setOnClickListener {
                    fcl.swapFragment(QuestionFragment(test, section, navIndex, NO_QUESTION), false)
                }
            } else {
                button.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupNextButton(button: Button) {
        val fcl = activity as FragmentChangeListener
        if (question < 0 || test.sections[section].questions[question].number < test.questionsCount) {
            var nextQuestion = question + 1
            var nextSection = section
            if (nextQuestion >= test.sections[section].questionCount) {
                nextQuestion = NO_QUESTION
                nextSection++
            }
            button.setOnClickListener {
                fcl.swapFragment(QuestionFragment(test, nextSection, nextQuestion, NO_QUESTION), false)
            }
        } else {
            button.text = getString(R.string.test_finish_button)
            button.setOnClickListener {
                fcl.swapFragment(ResultFragment(calculateAndSaveResult()), true)
            }
        }
    }

    private fun calculateAndSaveResult(): Result {
        var correct = 0
        var points = 0
        test.sections.forEach { section ->
            section.questions.forEach { question ->
                if (question.type == QuestionType.OPEN) {
                    val correctAnswers = question.correctAnswer.split("/")
                    if (correctAnswers.contains(question.userAnswer)) {
                        correct++
                        points += question.points
                    }
                } else {
                    if (question.userAnswer == question.correctAnswer) {
                        correct++
                        points += question.points
                    }
                }
            }
        }
        val result = Result(0, test.setting, Date(), test.questionsCount, correct, points,
            test.maxPoints)
        test.result = result

        CoroutineScope(Dispatchers.IO).launch {
            context?.let { context ->
                StudentTestsDatabase.getInstance(context).resultDao().insert(result)
            }
        }

        return result
    }

    private class OpenQuestionAnswerChangeListener(private val question: Question) : TextWatcher {
        override fun afterTextChanged(edit: Editable?) {
            question.userAnswer = edit.toString()
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}