package org.deepak;

import org.deepak.dto.PieceType;
import org.deepak.dto.Player;
import org.deepak.dto.PlayingPiece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        TicTacToeGame ticTacToeGame = new TicTacToeGame();
        PlayingPiece crossPiece = new PlayingPiece(PieceType.X);

        Player player1 = new Player("Player1", crossPiece);
        PlayingPiece noughtsPiece = new PlayingPiece(PieceType.O);
        Player player2 = new Player("Player2", noughtsPiece);
        ticTacToeGame.initializeGame(player1, player2);
        ticTacToeGame.printScoreBoard();

        String winner = ticTacToeGame.startGame();
        logger.info("Game winner is: {}", winner);
        ticTacToeGame.printScoreBoard();
    }
}