package com.example.tictactoe

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.tictactoe.models.MatchEndDialogData

class MatchEndDialog {
    companion object {
        fun showDialog(context: Context, matchEndDialog: MatchEndDialogData){
            val view = LayoutInflater.from(context).inflate(R.layout.match_end_dialog, null)
            setUpView(view, matchEndDialog)
            val dialog = AlertDialog.Builder(context)
                 .setView(view)
                .create()

            view.findViewById<Button>(R.id.continue_button).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        private fun setUpView(view: View, matchEndDialogData: MatchEndDialogData){
            view.findViewById<TextView>(R.id.winner_player_text).apply {
                if (matchEndDialogData.isWin){
                    text = matchEndDialogData.playerPlaceholderMark.name
                } else {
                    visibility = View.GONE
                }
                view.findViewById<TextView>(R.id.match_end_text).apply {
                    text = matchEndDialogData.text
                }
            }
        }
    }
}