package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.core.view.get
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
    private lateinit var gridAdapter: GridAdapter
    private lateinit var gridView: GridView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gridAdapter = GridAdapter(requireContext(), viewModel.gameData)
        gridView = view.findViewById<GridView>(R.id.game_grid).apply {
            adapter = gridAdapter
            onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, id ->
                    viewModel.onPositionSelected(position)
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
        // TODO(): This is not the ideal way to update the view. Update.
        gridView[position].findViewById<Button>(R.id.button).apply {
            text = if (viewModel.currentPlayer.placeHolderMark == PlaceholderMark.X) {
                context.getString(R.string.player_placeholder_x)
            } else {
                context.getString(R.string.player_placeholder_o)
            }
        }
    }

    override fun onInvalidPositionSelected(placeholderMark: PlaceholderMark) {
        Toast.makeText(
            context,
            "Invalid Move by ${placeholderMark.name}. Try Again.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDraw() {
        TODO("Not yet implemented")
    }

    override fun onWin(player: Player) {
        TODO("Not yet implemented")
    }
    // end
}
