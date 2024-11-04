package edu.gmu.cs477.fall2020.tictactoegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button [][]buttons;
    private TicTacToe tttGame;
    private TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        tttGame = new TicTacToe();
        buildGuiByCode();
    }
    public void buildGuiByCode() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / TicTacToe.SIDE;
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE + 1);  // space for a message
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        ButtonHandler bh = new ButtonHandler();
        for (int i=0;i<TicTacToe.SIDE;i++)
            for (int j=0;j<TicTacToe.SIDE;j++) {
                buttons[i][j] = new Button(this);
                buttons[i][j].setTextSize((float) ((int)w*.2));
                buttons[i][j].setOnClickListener(bh);
                gridLayout.addView(buttons[i][j], w, w);
            }
        status = new TextView(this);
            GridLayout.Spec rowSpec = GridLayout.spec(TicTacToe.SIDE,1);
            GridLayout.Spec columnSpec = GridLayout.spec(0,TicTacToe.SIDE);
            GridLayout.LayoutParams lpStatus =
                    new GridLayout.LayoutParams(rowSpec,columnSpec);
            status.setLayoutParams(lpStatus);
            status.setWidth(TicTacToe.SIDE*w);
            status.setHeight(w);
            status.setGravity(Gravity.CENTER);
            status.setBackgroundColor(Color.GREEN);
            status.setTextSize((int)(w*.15));
            status.setText(tttGame.result());
            gridLayout.addView(status);

        setContentView(gridLayout);

    }

    public void update(int row, int col) {
        int play = tttGame.play(row,col);
        if (play == 1) buttons[row][col].setText("X");
        else if (play == 2) buttons[row][col].setText("O");
        if (tttGame.isGameOver() ) {
            enableButtons(false);
            status.setBackgroundColor(Color.RED);
            status.setText(tttGame.result());
            showNewGameDialog();
        }
    }
    public void enableButtons(boolean enabled) {
        for (int row=0;row<TicTacToe.SIDE;row++)
            for (int col=0;col<TicTacToe.SIDE;col++)
                buttons[row][col].setEnabled(enabled);
    }
    public void resetButtons() {
        for (int row=0;row<TicTacToe.SIDE;row++)
            for (int col=0;col<TicTacToe.SIDE;col++)
                buttons[row][col].setText("");
    }
    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            for (int row = 0;row<TicTacToe.SIDE;row++)
                for (int column=0;column<TicTacToe.SIDE;column++)
                    if (v == buttons[row][column])
                        update(row,column);
        }
    }

    public void showNewGameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("This is fun");
        alert.setMessage("Play again??");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("Yes", playAgain);
        alert.setNegativeButton("No",playAgain);
        alert.show();

    }
    private class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            if (id == -1) {  // yes
                tttGame.resetGame();
                enableButtons(true);
                resetButtons();
                status.setBackgroundColor(Color.GREEN);
                status.setText(tttGame.result());
            } else if (id == -2)  // no
                MainActivity.this.finish();
        }
    }
}