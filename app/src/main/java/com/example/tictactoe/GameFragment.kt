package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tictactoe.models.PlaceholderMark

/**
 * A [Fragment] subclass for game screen.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment(R.layout.fragment_game) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<GridView>(R.id.game_grid).apply {
            adapter = GridAdapter(requireContext(), getInitialGameData())
            onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, id ->
                    // TODO(): Handle click listeners
                    val msg = "Position=$position, Id=$id"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
        }
    }

    // TODO() : Move this to viewModel later
    private fun getInitialGameData() = List(9) { PlaceholderMark.EMPTY }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment GameFragment.
         */
        @JvmStatic
        fun newInstance() = GameFragment()
    }
}
