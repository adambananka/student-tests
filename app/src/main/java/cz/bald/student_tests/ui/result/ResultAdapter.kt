package cz.bald.student_tests.ui.result

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.bald.student_tests.extension.toPrintable
import cz.bald.student_tests.model.Result
import cz.bald.studenttests.R
import kotlinx.android.synthetic.main.item_result.view.*

class ResultAdapter(private val results: List<Result>) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private lateinit var  resource: Resources

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        resource = parent.resources
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int = results.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(results[position])
    }

    inner class ResultViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(result: Result) {
            view.result_item_date.text = result.date.toPrintable()
            view.result_item_setting_header.text = resource.getString(
                R.string.result_list_setting_header, result.test.subject.getSubjectName(), result.test.year)
            view.result_item_setting_bottom.text = resource.getString(
                R.string.result_list_setting_bottom, result.test.type, result.test.language)
            view.result_item_questions.text = resource.getString(
                R.string.result_list_questions, result.correctQuestions, result.questions)
            view.result_item_points.text = resource.getString(R.string.result_list_points,
                result.points, result.maxPoints)
            val pct = 100.0 * result.points / result.maxPoints
            view.result_item_percentage.text = resource.getString(
                R.string.result_list_percentage, pct)
        }
    }
}