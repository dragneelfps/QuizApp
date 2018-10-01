package com.nooblabs.srawa.quizapp.ui.quizlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.ui.newquiz.NewQuizActivity
import com.nooblabs.srawa.quizapp.ui.quiz.QuizFragment
import kotlinx.android.synthetic.main.quiz_list_main.view.*


class QuizListFragment : Fragment() {

    private lateinit var model: QuizListViewModel
    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(QuizListViewModel::class.java)
        adapter = QuizListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.quiz_list_main, container, false)
        view.quiz_list_view.layoutManager = LinearLayoutManager(requireContext())
        view.quiz_list_view.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading()

        adapter.onPlayListener = object : QuizListAdapter.OnPlayListener {
            override fun onPlay(quizId: Long) {
                fragmentManager?.let {
                    val quizFragment = QuizFragment().apply {
                        arguments = Bundle().apply {
                            putLong(QuizFragment.QUIZ_ID_KEY, quizId)
                        }
                    }
                    it.beginTransaction().replace(R.id.content_frame, quizFragment).commit()
                }
            }
        }

        model.getAllQuizzes().observe(this, Observer {
            hideLoading()
            Log.d("dev", "Found ${it?.size ?: 0} quizzes")
            adapter.quizData = it
            it?.forEach {
                model.getQuizStat(it.quizId!!).observe(this, Observer { quizStatList ->
                    quizStatList ?: return@Observer
                    if (quizStatList.isEmpty()) return@Observer
                    val stat = quizStatList[0]
                    adapter.quizStats[it.quizId!!] = QuizListAdapter.QuizStat(stat.successRate, stat.gamesPlayed)
                    adapter.notifyDataSetChanged()
                })
            }
        })

        view.new_quiz_button.setOnClickListener {
            val intent = Intent(requireActivity(), NewQuizActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading() {
        view?.apply {
            progress_bar.visibility = View.VISIBLE
            new_quiz_button.hide()
        }
    }

    private fun hideLoading() {
        view?.apply {
            progress_bar.visibility = View.GONE
            new_quiz_button.show()
        }
    }


}
