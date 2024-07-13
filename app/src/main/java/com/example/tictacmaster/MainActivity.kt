package com.example.tictacmaster

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private val board = Array(3) { Array(3) { "" } }
    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        initializeButtons()

        val resetButton: Button = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initializeButtons() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
            button.setOnClickListener {
                onGridButtonClick(button, i / 3, i % 3)
            }
        }
    }

    private fun onGridButtonClick(button: Button, row: Int, col: Int) {
        if (button.text.isEmpty()) {
            button.text = currentPlayer
            board[row][col] = currentPlayer

            if (checkWin(currentPlayer)) {
                Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_LONG).show()
                disableButtons()
            } else if (isBoardFull()) {
                Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show()
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    private fun checkWin(player: String): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if (board[i].all { it == player } || board.map { it[i] }.all { it == player }) {
                return true
            }
        }
        // Check diagonals
        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        return board.flatten().none { it.isEmpty() }
    }

    private fun disableButtons() {
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.isEnabled = false
        }
    }

    private fun resetGame() {
        currentPlayer = "X"
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = ""
            }
        }
        initializeButtons()
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.isEnabled = true
        }
    }
}
