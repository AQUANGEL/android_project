package com.vladlozkin.libgdk_protector

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ScoreListAdapter (private val context: Activity, private val scores: Array<Score>)
                        : ArrayAdapter<Score>(context, R.layout.score_list, scores) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.score_list, null, true)

        val rank = rowView.findViewById(R.id.rank) as TextView
        val userName = rowView.findViewById(R.id.name) as TextView
        val score = rowView.findViewById(R.id.score) as TextView

        val scoreObj = scores[position]

        rank.text = (position + 1).toString()
        userName.text = scoreObj.user_name
        score.text = scoreObj.score.toString()

        return rowView
    }
}