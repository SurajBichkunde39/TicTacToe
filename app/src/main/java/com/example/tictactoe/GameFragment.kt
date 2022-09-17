package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.models.BoardManager
import com.example.tictactoe.models.MatchEndDialogData
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

    private lateinit var player1Card: CardView
    private lateinit var player2Card: CardView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViews(view)
        setUpGridView()
        viewModel.setBoardManager(this)
    }

    private fun initializeViews(view: View) {
        with(view) {
            gridView = findViewById(R.id.game_grid)
            player1Card = findViewById<CardView?>(R.id.player_1_card).apply {
                findViewById<Button>(R.id.player_1_button).text =
                    viewModel.player1.placeHolderMark.name
            }
            player2Card = findViewById<CardView?>(R.id.player_2_card).apply {
                findViewById<Button>(R.id.player_2_button).text =
                    viewModel.player2.placeHolderMark.name
            }
        }
    }

    private fun setUpGridView() {
        setUpGridAdapter()
        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.onPositionSelected(position)
            }
    }

    private fun setUpGridAdapter(invalidateData: Boolean = false) {
        if (invalidateData) {
            gridAdapter.notifyDataSetInvalidated()
            viewModel.resetGameData()
        }
        gridAdapter = GridAdapter(requireContext(), viewModel.gameData)
        gridView.adapter = gridAdapter
    }


    // start: BoardManager
    override fun onCurrentPlayerUpdated(currentPlayerNumber: Int) {
        if (currentPlayerNumber == 1) {
            player1Card.setCardBackgroundColor(resources.getColor(R.color.card_selected))
            player2Card.setCardBackgroundColor(resources.getColor(R.color.card_default))
        } else {
            player2Card.setCardBackgroundColor(resources.getColor(R.color.card_selected))
            player1Card.setCardBackgroundColor(resources.getColor(R.color.card_default))
        }
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
        MatchEndDialog.showDialog(
            requireContext(), MatchEndDialogData(
                isWin = false,
                text = resources.getString(R.string.match_draw_text),
                playerPlaceholderMark = PlaceholderMark.EMPTY
            )
        )
        setUpGridAdapter(invalidateData = true)
    }

    override fun onWin(player: Player) {
        MatchEndDialog.showDialog(
            requireContext(), MatchEndDialogData(
                isWin = true,
                text = resources.getString(R.string.match_won_text, player.placeHolderMark.name),
                playerPlaceholderMark = player.placeHolderMark
            )
        )
        setUpGridAdapter(invalidateData = true)
    }
    // end

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
