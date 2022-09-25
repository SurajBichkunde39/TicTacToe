package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.dialogs.ExitDialog
import com.example.tictactoe.dialogs.ExitDialogData
import com.example.tictactoe.dialogs.MatchEndDialog
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

    private var backPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ExitDialog.showDialog(getExitDialogData())
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViews(view)
        setUpGridView()
        setUpBackPress()
        updateScores()
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
            viewModel.resetGridData()
        }
        gridAdapter = GridAdapter(requireContext(), viewModel.gameData)
        gridView.adapter = gridAdapter
    }

    private fun setUpBackPress() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(backPressedCallback)
    }

    private fun handleBackPressed() {
        viewModel.resetGameData()
        backPressedCallback.isEnabled = false
        requireActivity().onBackPressed()
    }

    private fun getExitDialogData() = ExitDialogData(
        this.requireContext(),
        R.string.exit_dialog_title,
        R.string.exit_dialog_message,
        { _, _ -> handleBackPressed() },
        { dialogInterface, _ -> dialogInterface.dismiss() }
    )

    private fun updateScores() {
        player1Card.findViewById<TextView>(R.id.score_player_1).text =
            resources.getString(R.string.score_sting, viewModel.player1.score)
        player2Card.findViewById<TextView>(R.id.score_player_2).text =
            resources.getString(R.string.score_sting, viewModel.player2.score)
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
        updateScores()
        MatchEndDialog.showDialog(
            requireContext(), MatchEndDialogData(
                isWin = false,
                text = resources.getString(R.string.match_draw_text),
                playerPlaceholderMark = PlaceholderMark.EMPTY
            )
        )
        setUpGridAdapter(invalidateData = true)
        viewModel.checkForGameOver()
    }

    override fun onWin(player: Player) {
        updateScores()
        MatchEndDialog.showDialog(
            requireContext(), MatchEndDialogData(
                isWin = true,
                text = resources.getString(R.string.match_won_text, player.placeHolderMark.name),
                playerPlaceholderMark = player.placeHolderMark
            )
        )
        setUpGridAdapter(invalidateData = true)
        viewModel.checkForGameOver()
    }

    override fun onGameOver(winner: Player) {
        Toast.makeText(
            context,
            "Game over. ${winner.placeHolderMark} won the game.",
            Toast.LENGTH_LONG
        ).show()
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
