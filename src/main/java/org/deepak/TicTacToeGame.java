package org.deepak;

import lombok.Data;
import org.deepak.dto.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

@Data
public class TicTacToeGame {
    private static final Logger logger = LoggerFactory.getLogger(TicTacToeGame.class);
    private Board gameBoard;
    private ScoreCard scoreCard = new ScoreCard();
    private Scanner scanner = new Scanner(System.in);
    private Deque<Player> players = new ArrayDeque<>();
    private Player player1, player2;
    private List<GameObserver> observers = new ArrayList<>();

    public void initializeGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        players.add(player1);
        players.add(player2);
        this.gameBoard = new Board(3);
        addObserver(scoreCard);
    }
    public void printScoreBoard() {
        Pair<Integer,Integer> currentScore = scoreCard.getCurrentScore(player1, player2);
        logger.info("Current Score - {}: {}, {}: {}", 
                   player1.getPlayerName(), currentScore.getKey(),
                   player2.getPlayerName(), currentScore.getValue());
    }

    public String startGame() {

        boolean noWinner = true;
        while (noWinner) {
            Player player = players.removeFirst();
            gameBoard.printBoard();
            if(gameBoard.isBoardFull()) {
                noWinner = false;
            }
            System.out.print("Player:" + player.getPlayerName() + " Enter row,column: ");
            String input = scanner.nextLine();
            String[] values = input.split(",");
            
            if(values.length != 2) {
                logger.warn("Invalid input format from {}: '{}'. Expected row,column format", 
                           player.getPlayerName(), input);
                players.addFirst(player);
                continue;
            }
            
            int inputRow, inputColumn;
            try {
                inputRow = Integer.parseInt(values[0].trim());
                inputColumn = Integer.parseInt(values[1].trim());
            } catch(NumberFormatException e) {
                logger.warn("Invalid numbers from {}: '{}'. Expected integers 0-2", 
                           player.getPlayerName(), input);
                players.addFirst(player);
                continue;
            }
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow, inputColumn, player.getPlayingPiece());

            if(!pieceAddedSuccessfully) {
                logger.warn("Invalid position chosen by {}: ({},{})", 
                           player.getPlayerName(), inputRow, inputColumn);
                players.addFirst(player);
            } else {
                players.addLast(player);
            }

            boolean winner = isThereWinner(inputRow, inputColumn, player.getPlayingPiece().getPieceType());
            if(winner){
                notifyObservers(player);
                return player.getPlayerName();
            }
        }
        return "Tie";
    }

    private boolean isThereWinner(int inputRow, int inputColumn, PieceType pieceType) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i = 0; i< gameBoard.getSize(); i++) {
            if(gameBoard.getBoard()[inputRow][i] == null || gameBoard.getBoard()[inputRow][i].getPieceType() != pieceType) {
                rowMatch = false;
                break;
            }
        }

        //need to check in column
        for(int i = 0; i< gameBoard.getSize(); i++) {
            if(gameBoard.getBoard()[i][inputColumn] == null || gameBoard.getBoard()[i][inputColumn].getPieceType() != pieceType) {
                columnMatch = false;
                break;
            }
        }

        //need to check diagonals
        for(int i = 0, j = 0; i< gameBoard.getSize(); i++,j++) {
            if (gameBoard.getBoard()[i][j] == null || gameBoard.getBoard()[i][j].getPieceType() != pieceType) {
                diagonalMatch = false;
                break;
            }
        }

        //need to check anti-diagonals
        for(int i = 0, j = gameBoard.getSize() -1; i< gameBoard.getSize(); i++,j--) {
            if (gameBoard.getBoard()[i][j] == null || gameBoard.getBoard()[i][j].getPieceType() != pieceType) {
                antiDiagonalMatch = false;
                break;
            }
        }
        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }
    
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers(Player winner) {
        for (GameObserver observer : observers) {
            observer.onGameEnd(winner, player1, player2);
        }
    }
}
