package cz.bald.student_tests.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import cz.bald.student_tests.model.TestSetting
import cz.bald.studenttests.R
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import kotlinx.android.synthetic.main.fragment_setup_year.view.*

class YearFragment(private val testSetting: TestSetting) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_setup_year, container, false)

        view.year_selection_text_view.text = testSetting.type.name + " - " + testSetting.language.name + " - " + testSetting.subject.getSubjectName()

        view.year_list.adapter = ArrayAdapter<Int>(this.requireContext(),
            android.R.layout.simple_list_item_1, testSetting.subject.getYearsOfSubject())

        view.year_list.setOnItemClickListener { adapterView, _, i, _ ->
            val fcl = activity as FragmentChangeListener
            testSetting.year = adapterView.getItemAtPosition(i) as Int
            fcl.swapFragment(ReviewFragment(testSetting), true)
        }

        return view
    }
}