package cz.bald.student_tests.ui.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import cz.bald.studenttests.R
import cz.bald.student_tests.ui.listener.FragmentChangeListener
import kotlinx.android.synthetic.main.fragment_setup_year.view.*

class YearFragment(private val testSetting: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_setup_year, container, false)

        view.year_selection_text_view.text = testSetting

        view.year_list.adapter = ArrayAdapter<String>(this.requireContext(),
            android.R.layout.simple_list_item_1, arrayOf("2005", "2006", "2007", "2008", "2009",
                "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"))

        view.year_list.setOnItemClickListener { adapterView, _, i, _ ->
            val fcl = activity as FragmentChangeListener
            fcl.swapFragment(ReviewFragment(testSetting + " - " + adapterView.getItemAtPosition(i)))
        }

        return view
    }
}