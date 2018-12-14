package com.example.tyler.simplechess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyler.simplechess.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import static com.example.tyler.simplechess.model.Mover.move;

public class GameActivity extends AppCompatActivity {

    private ImageView[][] chessBoard = new ImageView[8][8];
    private String[] moveHolder = new String[2];

    private int colorInt;
    private int moveCount = 0;
    private View prevV;

    GameBoard board;
    GameBoard prevStateBoard;
    boolean primaryMoveMade = false;
    boolean moveHappens;

    ArrayList<String[]> listOfMoves = new ArrayList<>();

    private FileOutputStream fos = null;
    private FileInputStream fis = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initBoard();
    }

    private void saveGame(){
//        try {
//            fos = new FileOutputStream(new File(getFilesDir(), "Moves.ser"));
//            oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(listOfMoves);
//            oos.flush();
//            oos.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
        AlertDialog.Builder nameFile = new AlertDialog.Builder(GameActivity.this);
        final EditText input = new EditText(this);
        nameFile.setView(input);
        nameFile.setTitle("Save as").setMessage("Enter a file name:").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = input.getText().toString();
                try {
                    File outputDir = getFilesDir();
                    File moves = new File(outputDir, fileName + ".ser");
                    FileOutputStream fileOutputStream = new FileOutputStream(moves);
                    ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                    out.writeObject(listOfMoves);
                    out.close();
                    fileOutputStream.close();
                    System.out.println("SAVED FILE");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }).show();
    }

    private void refreshBoard(){
        TextView currentTurnText = findViewById(R.id.textTurn);
        String currentTurn = board.turnCount % 2 == 0 ? "Black move":"White move";
        currentTurnText.setText(currentTurn);
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

    public void draw(View v){
        Piece.Side oppositeSide = board.turnCount % 2 == 1 ? Piece.Side.BLACK:Piece.Side.WHITE;
        AlertDialog.Builder drawDialog = new AlertDialog.Builder(GameActivity.this);
        drawDialog.setTitle("Draw?").setMessage("Does " + oppositeSide.toString() + " accept?").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder drawDialog = new AlertDialog.Builder(GameActivity.this);
                drawDialog.setTitle("It's a draw!").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //end the game
                        AlertDialog.Builder saveGameDialog = new AlertDialog.Builder(GameActivity.this);
                        saveGameDialog.setTitle("Save Game").setMessage("Do you want to save this game?").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveGame();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //return to home
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).show();
                    }
                }).show();
            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // reject draw
            }
        }).show();
    }

    public void resign(View v){
        AlertDialog.Builder resignDialog = new AlertDialog.Builder(GameActivity.this);
        resignDialog.setTitle("Resign")
                .setMessage("Are you sure you want to give up?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Piece.Side winSide = board.turnCount % 2 == 1 ? Piece.Side.BLACK:Piece.Side.WHITE;
                        AlertDialog.Builder winnerDialog = new AlertDialog.Builder(GameActivity.this);
                        winnerDialog.setTitle(winSide.toString() + " wins!").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //end the game
                                AlertDialog.Builder saveGameDialog = new AlertDialog.Builder(GameActivity.this);
                                saveGameDialog.setTitle("Save Game").setMessage("Do you want to save this game?").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        saveGame();
                                    }
                                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //return to home
                                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).show();
                            }
                        }).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                }).show();
    }

    public void undoMove(View v){
        if (primaryMoveMade && moveCount > 0) {
            board = prevStateBoard;
            refreshBoard();
            listOfMoves.remove(listOfMoves.size()-1);
            moveCount--;
        }
    }

    public void randomMove(View v){
        int numPossibleMoves = 0;
        ArrayList<int[]> possibleMoves = new ArrayList<>();
        Piece.Side currentSide = board.turnCount % 2 == 0 ? Piece.Side.BLACK: Piece.Side.WHITE;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.getPiece(i,j) != null && board.getPiece(i,j).side == currentSide){
                    for (int m = 0; m < 8; m++){
                        for (int n = 0; n < 8; n++){
                            if(MoveCheck.isValid(i,j,m,n,board)) {
                                numPossibleMoves++;
                                possibleMoves.add(new int[] {i,j,m,n});
                            }
                        }
                    }
                }
            }
        }
        System.out.println(numPossibleMoves);
        Random rand = new Random();
        int randomMove = rand.nextInt(numPossibleMoves);
        String[] moveInput = new String[2];
        moveInput[0] = Integer.toString(possibleMoves.get(randomMove)[0]) + Integer.toString(possibleMoves.get(randomMove)[1]);
        moveInput[1] = Integer.toString(possibleMoves.get(randomMove)[2]) + Integer.toString(possibleMoves.get(randomMove)[3]);
        prevStateBoard = GameBoard.cleanCopy(board);
        move(moveInput, board);
        primaryMoveMade = true;
        listOfMoves.add(new String[] {moveInput[0], moveInput[1]});
        refreshBoard();
        moveCount++;
    }

    private void setBackground(int file, int rank){
        if ((file + rank) % 2 == 0)
            chessBoard[file][rank].setBackgroundColor(getResources().getColor(R.color.blackBlock));
        else
            chessBoard[file][rank].setBackgroundColor(getResources().getColor(R.color.whiteBlock));
    }

    private void initBoard(){

        board = new GameBoard();
        board.initBoard();
        TextView currentTurnText = findViewById(R.id.textTurn);
        String currentTurn = board.turnCount % 2 == 0 ? "Black move":"White move";
        currentTurnText.setText(currentTurn);

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
            for (int j = 0; j < 8; j++){
                setBackground(i,j);
                chessBoard[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println(v.getTag());
                        if (moveHolder[0] == null){
                            moveHolder[0] = v.getTag().toString();

                            int hold1 = Character.getNumericValue(moveHolder[0].charAt(0));
                            int hold2 = Character.getNumericValue(moveHolder[0].charAt(1));
                            colorInt = hold1 + hold2;

                            v.setBackgroundColor(getResources().getColor(R.color.selected));
                            prevV = v;
                        }
                        // MAIN MOVER
                        else{
                            moveHolder[1] = v.getTag().toString();
                            GameBoard copyBoard2 = null;
                            if (prevStateBoard != null)
                                copyBoard2 = GameBoard.cleanCopy(prevStateBoard);
                            prevStateBoard = GameBoard.cleanCopy(board);
                            moveHappens = move(moveHolder, board);
                            if (moveHappens){
                                moveCount++;
                                listOfMoves.add(new String[] {moveHolder[0], moveHolder[1]});
                                for (String[] strArr : listOfMoves)
                                    System.out.println("MOVE:" + strArr[0] + " " + strArr[1]);
                                primaryMoveMade = true;
                                board.printBoard();
                                refreshBoard();
                                if (Checkmate.isCheckmate(board)){
                                    String winner = board.turnCount % 2 == 0 ? "White Wins!" : "Black Wins!";
                                    AlertDialog.Builder winDialog = new AlertDialog.Builder(GameActivity.this);
                                    winDialog.setTitle(winner).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AlertDialog.Builder saveGameDialog = new AlertDialog.Builder(GameActivity.this);
                                            saveGameDialog.setTitle("Save Game").setMessage("Do you want to save this game?").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    saveGame();
                                                }
                                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //return to home
                                                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            }).show();
                                        }
                                    }).show();
                                }
                                else if (Checkmate.isStalemate(board)){
                                    AlertDialog.Builder winDialog = new AlertDialog.Builder(GameActivity.this);
                                    winDialog.setTitle("Stalemate").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AlertDialog.Builder saveGameDialog = new AlertDialog.Builder(GameActivity.this);
                                            saveGameDialog.setTitle("Save Game").setMessage("Do you want to save this game?").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    saveGame();
                                                }
                                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //return to home
                                                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            }).show();
                                        }
                                    }).show();
                                }
                            }
                            else{
                                if(primaryMoveMade) {
                                    prevStateBoard = GameBoard.cleanCopy(copyBoard2);
                                    refreshBoard();
                                }
                            }

                            if (colorInt % 2 == 0)
                                prevV.setBackgroundColor(getResources().getColor(R.color.blackBlock));
                            else
                                prevV.setBackgroundColor(getResources().getColor(R.color.whiteBlock));

                            moveHolder[0] = null;
                            moveHolder[1] = null;


                        }
                    }
                });

            }
        }
    }


}
