package com.vladlozkin.libgdk_protector


import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.add_score_dialog.*
import kotlin.concurrent.thread

class AddScoreDialog     // TODO Auto-generated constructor stub
(var c: Activity, var score: Int) : Dialog(c), View.OnClickListener {
    var d: Dialog? = null
    var yes: Button? = null
    var no: Button? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.add_score_dialog)

        addScore.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if (userName.text.isNotEmpty()) {
            var scores : List<Score> = listOf<Score>()
            val fetchDb = thread {
                val db = Room.databaseBuilder(c, AppDatabase::class.java,
                        "database-name").build()

                scores = db.scoreDao().getAll()

                db.close()
            }
            fetchDb.join()

            val insertDb = thread {
                val db = Room.databaseBuilder(c, AppDatabase::class.java,
                        "database-name").build()

                db.scoreDao().insertAll(Score(scores.size + 1, userName.text.toString(), score))

                db.close()
            }
            insertDb.join()
            dismiss()
        } else {
            Toast.makeText(c, context.getString(R.string.enter_name) , Toast.LENGTH_SHORT).show()
        }
    }
}