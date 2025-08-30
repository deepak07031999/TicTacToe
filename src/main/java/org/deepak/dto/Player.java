package org.deepak.dto;

import lombok.Data;

@Data
public class Player {
    private String playerName;
    private PlayingPiece playingPiece;
    
    public Player(String playerName, PlayingPiece playingPiece) {
        this.playerName = playerName;
        this.playingPiece = playingPiece;
    }
}
