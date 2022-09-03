package com.example.tictactoe.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.example.tictactoe.R

class GridAdapter(
    private val context: Context,
    private val placeholderMarks: List<PlaceholderMark>,
) : BaseAdapter() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount() = placeholderMarks.size

    override fun getItem(p0: Int) = placeholderMarks[p0]

    override fun getItemId(p0: Int) = p0.toLong()

    override fun getView(position: Int, contentView: View?, p2: ViewGroup?): View {
        val view: View
        if (contentView != null) return contentView
        else {
            view = layoutInflater.inflate(R.layout.game_grid_item, null)
        }
        val buttonText = when (placeholderMarks[position]) {
            PlaceholderMark.X -> "X"
            PlaceholderMark.O -> "O"
            PlaceholderMark.EMPTY -> ""
        }
        view.findViewById<Button>(R.id.button).apply {
            text = buttonText
        }
        return view
    }
}
