package cz.bald.student_tests.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import cz.bald.student_tests.model.Result
import cz.bald.studenttests.R
import kotlinx.android.synthetic.main.fragment_result_list.view.*


class ResultListFragment(private val results: List<Result>) : Fragment() {

    private val resultAdapter = ResultAdapter(results)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_result_list, container, false)

        view.result_list_recycler.layoutManager = LinearLayoutManager(context)
        view.result_list_recycler.adapter = resultAdapter
        view.result_list_recycler.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        if (results.isEmpty()) {
            view.result_list_no_results.visibility = View.VISIBLE
        }

        return view
    }
}