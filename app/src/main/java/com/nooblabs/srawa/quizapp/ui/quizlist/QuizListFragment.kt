package com.nooblabs.srawa.quizapp.ui.quizlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nooblabs.srawa.quizapp.R
import com.nooblabs.srawa.quizapp.ui.quiz.QuizFragment
import kotlinx.android.synthetic.main.quiz_list_main.view.*


class QuizListFragment : Fragment() {

    private lateinit var model: QuizListViewModel
    private lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProviders.of(this).get(QuizListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.quiz_list_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = QuizListAdapter()
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

        view.quiz_list_view.layoutManager = LinearLayoutManager(requireContext())
        view.quiz_list_view.adapter = adapter

        model.getQuizList().observe(this, Observer<List<QuizListViewModel.QuizStat>> { list ->
            list?.let {
                Log.d("dev","Found quizStat: $it")
                adapter.data = it
            }
        })

    }


}
