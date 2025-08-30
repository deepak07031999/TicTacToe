package org.deepak.dto;

import lombok.Getter;

public class PlayingPiece {
    @Getter
    private final PieceType pieceType;
    
    public PlayingPiece(PieceType pieceType) {
        this.pieceType = pieceType;
    }
}
