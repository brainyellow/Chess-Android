package com.example.tyler.simplechess.model;

import com.example.tyler.simplechess.R;
import com.example.tyler.simplechess.model.Piece.Rank;
import com.example.tyler.simplechess.model.Piece.Side;

public class NameManager {

    public static int PieceToImage(Side s, Rank r){
        if (s.equals(Side.WHITE)){
            if (r.equals(Rank.BISHOP))
                return R.drawable.bishop_white;
            if (r.equals(Rank.ROOK))
                return R.drawable.rook_white;
            if (r.equals(Rank.KING))
                return R.drawable.king_white;
            if (r.equals(Rank.KNIGHT))
                return R.drawable.knight_white;
            if (r.equals(Rank.PAWN))
                return R.drawable.pawn_white;
            if (r.equals(Rank.QUEEN))
                return R.drawable.queen_white;
        }
        if (s.equals(Side.BLACK)){
            if (r.equals(Rank.BISHOP))
                return R.drawable.bishop_black;
            if (r.equals(Rank.ROOK))
                return R.drawable.rook_black;
            if (r.equals(Rank.KING))
                return R.drawable.king_black;
            if (r.equals(Rank.KNIGHT))
                return R.drawable.knight_black;
            if (r.equals(Rank.PAWN))
                return R.drawable.pawn_black;
            if (r.equals(Rank.QUEEN))
                return R.drawable.queen_black;
        }

        return 0;
    }
}
