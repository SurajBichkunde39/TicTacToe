package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.models.BoardManager
import com.example.tictactoe.models.PlaceholderMark
import com.example.tictactoe.models.Player

/**
 * A [Fragment] subclass for game screen.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment(R.layout.fragment_game), BoardManager {

    private val viewModel by activityViewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<GridView>(R.id.game_grid).apply {
            adapter = GridAdapter(requireContext(), viewModel.gameData)
            onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, id ->
                    // TODO(): Handle click listeners
                    val msg = "Position=$position, Id=$id"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
        }
        viewModel.setBoardManager(this)
    }

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

    // start: BoardManager
    override fun onCurrentPlayerUpdated(currentPlayer: Player) {
        TODO("Not yet implemented")
    }

    override fun onPositionSelected(position: Int, placeholderMark: PlaceholderMark) {
        TODO("Not yet implemented")
    }

    override fun onInvalidPositionSelected(placeholderMark: PlaceholderMark) {
        TODO("Not yet implemented")
    }

    override fun onDraw() {
        TODO("Not yet implemented")
    }

    override fun onWin(player: Player) {
        TODO("Not yet implemented")
    }
    // end
}
