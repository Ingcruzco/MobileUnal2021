package com.unal.triqui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    lateinit var nInfoTextView:TextView
    private lateinit var mGame:TicTacToeGame
    private lateinit var mBoardView: BoardView
    private var mGameOver:Boolean=false
    private lateinit var mHumanMediaPlayer:MediaPlayer
    private lateinit var mComputerMediaPlayer:MediaPlayer
    companion object{
        const val DIALOG_DIFFICULTY_ID:Int=0
        const val DIALOG_QUIT_ID:Int=1
        const val DIALOG_ABOUT_ID:Int=2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mGame= TicTacToeGame()
        mBoardView=findViewById(R.id.board)
        mBoardView.setGame(mGame)
        nInfoTextView=findViewById(R.id.information)
        mBoardView.setOnTouchListener(mTouchListener())
        startGame()
    }

    override fun onResume() {
        super.onResume()
        mHumanMediaPlayer=MediaPlayer.create(applicationContext,R.raw.boom)
        mComputerMediaPlayer=MediaPlayer.create(applicationContext,R.raw.gunshot_9_mm)
    }

    override fun onPause() {
        super.onPause()
        mHumanMediaPlayer.release()
        mComputerMediaPlayer.release()
    }
    private fun startGame(){
        mGame.clearBoard()
        mBoardView.invalidate()
        nInfoTextView.setText("Vas primero")
        mGameOver=false
    }

    private fun setMove(player:Char,location: Int):Boolean{
        if (mGame.setMove(player,location)) {
            if (player==TicTacToeGame.HUMAN_PLAYER ) mHumanMediaPlayer.start() else mComputerMediaPlayer.start()
            mBoardView.invalidate()
            return true
        }
        return false
    }

    inner class mTouchListener:View.OnTouchListener {
        override  fun onTouch(view: View,event: MotionEvent):Boolean{
            var col:Int=event.getX().toInt()/mBoardView.getBoardCellWidth()
            var row:Int=event.getY().toInt()/mBoardView.getBoardCellHight()
            var pos:Int=row*3+col
            if(!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER,pos)){
                var winner = mGame.checkForWinner()
                if (winner == 0) {
                    nInfoTextView.setText("It's Android's turn.")
                    val move = mGame.getComputerMove()
                    Handler().postDelayed({
                        setMove(TicTacToeGame.COPUTER_PLAYER, move)
                        winner = mGame.checkForWinner()
                    },1000)
                }
                if (winner == 0) nInfoTextView.setText("It's your turn.") else if (winner == 1) {
                    nInfoTextView.setText(
                        "It's a tie!"
                    )
                    mGameOver=true
                } else if (winner == 2) nInfoTextView.setText("You won!") else {
                    nInfoTextView.setText(
                        "Android won!"
                    )
                    mGameOver=true
                }

            }
            return false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        var inflater:MenuInflater=getMenuInflater()
        inflater.inflate(R.menu.options_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.new_game -> {
                startGame()
                return true
            }
            R.id.ai_difficulty->{
                showDialog(DIALOG_DIFFICULTY_ID)
                return true
            }
            R.id.quit->{
                showDialog(DIALOG_QUIT_ID)
                return true
            }
            R.id.about->{
                showDialog(DIALOG_ABOUT_ID)
                return true
            }
        }
        return false
    }

    override fun onCreateDialog(id: Int): Dialog? {
        var dialog: Dialog? =null
        var difficult: String? =null
        var builder:AlertDialog.Builder=AlertDialog.Builder(this)
        when(id){
            0->{

                var levels:Array<String> = arrayOf(
                    resources.getString(R.string.difficulty_easy),
                    resources.getString(R.string.difficulty_harder),
                    resources.getString(R.string.difficulty_expert)
                )

                builder.setSingleChoiceItems(levels,1,DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                    dialog?.dismiss()
                    when(i){

                        0->{
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy)
                            difficult="Facil"
                        }
                        1->{
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder)
                            difficult="Dificil"
                        }
                        2->{
                            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert)
                            difficult="Eperto"
                        }
                    }
                    Toast.makeText(this,"Se modifico la dificultad a: " + difficult,Toast.LENGTH_LONG).show()

                })
                    .setTitle(R.string.difficulty_choose)
                dialog=builder.create()

            }
            1-> {
                builder.setMessage(R.string.quit_question)
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes,DialogInterface.OnClickListener{ dialog, which ->  
                        finish()
                    })
                    .setNegativeButton(R.string.no,null)
                dialog=builder.create()
            }
            2->{
                builder.setMessage(R.string.about_message)
                    .setCancelable(true)
                    .setPositiveButton(R.string.close,DialogInterface.OnClickListener{ dialog, which ->
                        dialog.dismiss()
                    })
                    .setTitle(R.string.about)
                dialog=builder.create()
            }
        }
        return dialog
    }

}

