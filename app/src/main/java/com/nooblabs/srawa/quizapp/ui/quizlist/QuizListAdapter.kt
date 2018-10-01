package com.nooblabs.srawa.quizapp.ui.quizlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.models.Quiz
import kotlinx.android.synthetic.main.quiz_list_item.view.*

class QuizListAdapter : RecyclerView.Adapter<QuizListAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView)

    var onPlayListener: OnPlayListener? = null

    var data: List<QuizListViewModel.QuizStat> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var quizData: List<Quiz>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var quizStats = HashMap<Long, QuizStat>()

    data class QuizStat(val successRate: Int, val totalAttempts: Int)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list_item, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val quiz = quizData!![position]
        val quizStat = quizStats[quiz.quizId]
        holder.itemView.apply {
            quiz_name_value.text = quiz.name
            success_rate_value.text = quizStat?.successRate?.toString() ?: "0"
            games_played_value.text = quizStat?.totalAttempts?.toString() ?: "0"
            play_quiz_btn.setOnClickListener { onPlayListener?.onPlay(quiz.quizId!!) }
        }
    }


    override fun getItemCount() = quizData?.size ?: 0


    interface OnPlayListener {
        fun onPlay(quizId: Long)
    }
}