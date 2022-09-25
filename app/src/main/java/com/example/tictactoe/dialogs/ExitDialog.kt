package com.example.tictactoe.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface.OnClickListener
import androidx.annotation.StringRes

class ExitDialog {

    companion object {
        fun showDialog(exitDialogData: ExitDialogData) {
            with(exitDialogData) {
                AlertDialog.Builder(context).setTitle(titleRes).setMessage(messageRes)
                    .setPositiveButton("Yes", positionClickAction)
                    .setNegativeButton("No", negativeClickListener).show()
            }
        }
    }
}

data class ExitDialogData(
    val context: Context,
    @StringRes val titleRes: Int,
    @StringRes val messageRes: Int,
    val positionClickAction: OnClickListener,
    val negativeClickListener: OnClickListener
)