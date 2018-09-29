package com.nooblabs.srawa.quizapp.ui.quizlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.srawa.quizapp.R
import kotlinx.android.synthetic.main.quiz_list_item.view.*

class QuizListAdapter: RecyclerView.Adapter<QuizListAdapter.VH>() {

    class VH(itemView: View): RecyclerView.ViewHolder(itemView)

    var onPlayListener: OnPlayListener? = null

    var data: List<QuizListViewModel.QuizStat> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list_item, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val quizStat = data[position]
        holder.itemView.apply {
            quiz_name_value.text = quizStat.quiz.name
            success_rate_value.text = quizStat.successRate.toString()
            games_played_value.text = quizStat.totalPlayed.toString()
            play_quiz_btn.setOnClickListener { onPlayListener?.onPlay(quizStat.quiz.quizId!!) }
        }
    }

    override fun getItemCount() = data.size



    interface OnPlayListener {
        fun onPlay(quizId: Long)
    }
}