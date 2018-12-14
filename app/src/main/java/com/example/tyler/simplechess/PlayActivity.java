package com.example.tyler.simplechess;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tyler.simplechess.model.GameBoard;
import com.example.tyler.simplechess.model.Mover;
import com.example.tyler.simplechess.model.NameManager;
import com.example.tyler.simplechess.model.Piece;

import java.util.ArrayList;

public class PlayActivity extends AppCompatActivity {

    private GameBoard board;
    private ImageView[][] chessBoard = new ImageView[8][8];
    ArrayList<String[]> listOfMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initPlayBackBoard();
        listOfMoves = (ArrayList<String[]>) getIntent().getSerializableExtra("listOfMoves");

    }

    private void initPlayBackBoard() {
        board = new GameBoard();
        board.initBoard();

        chessBoard[0][7] = (ImageView) findViewById(R.id.block07);
        chessBoard[1][7] = (ImageView) findViewById(R.id.block17);
        chessBoard[2][7] = (ImageView) findViewById(R.id.block27);
        chessBoard[3][7] = (ImageView) findViewById(R.id.block37);
        chessBoard[4][7] = (ImageView) findViewById(R.id.block47);
        chessBoard[5][7] = (ImageView) findViewById(R.id.block57);
        chessBoard[6][7] = (ImageView) findViewById(R.id.block67);
        chessBoard[7][7] = (ImageView) findViewById(R.id.block77);

        chessBoard[0][7].setTag("07");
        chessBoard[1][7].setTag("17");
        chessBoard[2][7].setTag("27");
        chessBoard[3][7].setTag("37");
        chessBoard[4][7].setTag("47");
        chessBoard[5][7].setTag("57");
        chessBoard[6][7].setTag("67");
        chessBoard[7][7].setTag("77");

        chessBoard[0][6] = (ImageView) findViewById(R.id.block06);
        chessBoard[1][6] = (ImageView) findViewById(R.id.block16);
        chessBoard[2][6] = (ImageView) findViewById(R.id.block26);
        chessBoard[3][6] = (ImageView) findViewById(R.id.block36);
        chessBoard[4][6] = (ImageView) findViewById(R.id.block46);
        chessBoard[5][6] = (ImageView) findViewById(R.id.block56);
        chessBoard[6][6] = (ImageView) findViewById(R.id.block66);
        chessBoard[7][6] = (ImageView) findViewById(R.id.block76);

        chessBoard[0][6].setTag("06");
        chessBoard[1][6].setTag("16");
        chessBoard[2][6].setTag("26");
        chessBoard[3][6].setTag("36");
        chessBoard[4][6].setTag("46");
        chessBoard[5][6].setTag("56");
        chessBoard[6][6].setTag("66");
        chessBoard[7][6].setTag("76");

        chessBoard[0][5] = (ImageView) findViewById(R.id.block05);
        chessBoard[1][5] = (ImageView) findViewById(R.id.block15);
        chessBoard[2][5] = (ImageView) findViewById(R.id.block25);
        chessBoard[3][5] = (ImageView) findViewById(R.id.block35);
        chessBoard[4][5] = (ImageView) findViewById(R.id.block45);
        chessBoard[5][5] = (ImageView) findViewById(R.id.block55);
        chessBoard[6][5] = (ImageView) findViewById(R.id.block65);
        chessBoard[7][5] = (ImageView) findViewById(R.id.block75);

        chessBoard[0][5].setTag("05");
        chessBoard[1][5].setTag("15");
        chessBoard[2][5].setTag("25");
        chessBoard[3][5].setTag("35");
        chessBoard[4][5].setTag("45");
        chessBoard[5][5].setTag("55");
        chessBoard[6][5].setTag("65");
        chessBoard[7][5].setTag("75");

        chessBoard[0][4] = (ImageView) findViewById(R.id.block04);
        chessBoard[1][4] = (ImageView) findViewById(R.id.block14);
        chessBoard[2][4] = (ImageView) findViewById(R.id.block24);
        chessBoard[3][4] = (ImageView) findViewById(R.id.block34);
        chessBoard[4][4] = (ImageView) findViewById(R.id.block44);
        chessBoard[5][4] = (ImageView) findViewById(R.id.block54);
        chessBoard[6][4] = (ImageView) findViewById(R.id.block64);
        chessBoard[7][4] = (ImageView) findViewById(R.id.block74);

        chessBoard[0][4].setTag("04");
        chessBoard[1][4].setTag("14");
        chessBoard[2][4].setTag("24");
        chessBoard[3][4].setTag("34");
        chessBoard[4][4].setTag("44");
        chessBoard[5][4].setTag("54");
        chessBoard[6][4].setTag("64");
        chessBoard[7][4].setTag("74");

        chessBoard[0][3] = (ImageView) findViewById(R.id.block03);
        chessBoard[1][3] = (ImageView) findViewById(R.id.block13);
        chessBoard[2][3] = (ImageView) findViewById(R.id.block23);
        chessBoard[3][3] = (ImageView) findViewById(R.id.block33);
        chessBoard[4][3] = (ImageView) findViewById(R.id.block43);
        chessBoard[5][3] = (ImageView) findViewById(R.id.block53);
        chessBoard[6][3] = (ImageView) findViewById(R.id.block63);
        chessBoard[7][3] = (ImageView) findViewById(R.id.block73);

        chessBoard[0][3].setTag("03");
        chessBoard[1][3].setTag("13");
        chessBoard[2][3].setTag("23");
        chessBoard[3][3].setTag("33");
        chessBoard[4][3].setTag("43");
        chessBoard[5][3].setTag("53");
        chessBoard[6][3].setTag("63");
        chessBoard[7][3].setTag("73");

        chessBoard[0][2] = (ImageView) findViewById(R.id.block02);
        chessBoard[1][2] = (ImageView) findViewById(R.id.block12);
        chessBoard[2][2] = (ImageView) findViewById(R.id.block22);
        chessBoard[3][2] = (ImageView) findViewById(R.id.block32);
        chessBoard[4][2] = (ImageView) findViewById(R.id.block42);
        chessBoard[5][2] = (ImageView) findViewById(R.id.block52);
        chessBoard[6][2] = (ImageView) findViewById(R.id.block62);
        chessBoard[7][2] = (ImageView) findViewById(R.id.block72);

        chessBoard[0][2].setTag("02");
        chessBoard[1][2].setTag("12");
        chessBoard[2][2].setTag("22");
        chessBoard[3][2].setTag("32");
        chessBoard[4][2].setTag("42");
        chessBoard[5][2].setTag("52");
        chessBoard[6][2].setTag("62");
        chessBoard[7][2].setTag("72");

        chessBoard[0][1] = (ImageView) findViewById(R.id.block01);
        chessBoard[1][1] = (ImageView) findViewById(R.id.block11);
        chessBoard[2][1] = (ImageView) findViewById(R.id.block21);
        chessBoard[3][1] = (ImageView) findViewById(R.id.block31);
        chessBoard[4][1] = (ImageView) findViewById(R.id.block41);
        chessBoard[5][1] = (ImageView) findViewById(R.id.block51);
        chessBoard[6][1] = (ImageView) findViewById(R.id.block61);
        chessBoard[7][1] = (ImageView) findViewById(R.id.block71);

        chessBoard[0][1].setTag("01");
        chessBoard[1][1].setTag("11");
        chessBoard[2][1].setTag("21");
        chessBoard[3][1].setTag("31");
        chessBoard[4][1].setTag("41");
        chessBoard[5][1].setTag("51");
        chessBoard[6][1].setTag("61");
        chessBoard[7][1].setTag("71");

        chessBoard[0][0] = (ImageView) findViewById(R.id.block00);
        chessBoard[1][0] = (ImageView) findViewById(R.id.block10);
        chessBoard[2][0] = (ImageView) findViewById(R.id.block20);
        chessBoard[3][0] = (ImageView) findViewById(R.id.block30);
        chessBoard[4][0] = (ImageView) findViewById(R.id.block40);
        chessBoard[5][0] = (ImageView) findViewById(R.id.block50);
        chessBoard[6][0] = (ImageView) findViewById(R.id.block60);
        chessBoard[7][0] = (ImageView) findViewById(R.id.block70);

        chessBoard[0][0].setTag("00");
        chessBoard[1][0].setTag("10");
        chessBoard[2][0].setTag("20");
        chessBoard[3][0].setTag("30");
        chessBoard[4][0].setTag("40");
        chessBoard[5][0].setTag("50");
        chessBoard[6][0].setTag("60");
        chessBoard[7][0].setTag("70");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setBackground(i, j);
            }
        }
    }

    private void setBackground(int file, int rank) {
        if ((file + rank) % 2 == 0)
            chessBoard[file][rank].setBackgroundColor(getResources().getColor(R.color.blackBlock));
        else
            chessBoard[file][rank].setBackgroundColor(getResources().getColor(R.color.whiteBlock));
    }
    int iterator = 0;
    public void nextMove(View v){
        System.out.println(iterator);
        if (iterator == listOfMoves.size())
        {
            AlertDialog.Builder doneDialog = new AlertDialog.Builder(this);
            doneDialog.setTitle("Replay finished").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
            return;
        }
        Mover.move(listOfMoves.get(iterator), board);
        refreshBoard();
        iterator++;
    }

    private void refreshBoard(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece p = board.getPiece(i, j);
                if (p == null){
                    chessBoard[i][j].setImageResource(R.drawable.empty);
                }
                else {
                    int img = NameManager.PieceToImage(p.side, p.rank);
                    chessBoard[i][j].setImageResource(img);
                }
            }
        }
    }
}