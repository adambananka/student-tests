package cz.bald.student_tests.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import cz.bald.studenttests.R
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import kotlinx.android.synthetic.main.fragment_setup_subject.view.*

class SubjectFragment(private val testSetting: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_setup_subject, container, false)

        view.subject_selection_text_view.text = testSetting

        view.subject_list.adapter = ArrayAdapter<String>(this.requireContext(),
            android.R.layout.simple_list_item_1, arrayOf("slovak", "english", "math"))

        view.subject_list.setOnItemClickListener{ adapterView, _, i, _ ->
            val fcl = activity as FragmentChangeListener
            fcl.swapFragment(YearFragment(testSetting + " - " + adapterView.getItemAtPosition(i)), true)
        }

        return view
    }
}