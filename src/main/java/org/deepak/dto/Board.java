package org.deepak.dto;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class Board {
    private static final Logger logger = LoggerFactory.getLogger(Board.class);
    private int size;
    private PlayingPiece[][]board;

    public Board(int size) {
        this.size = size;
        board = new PlayingPiece[size][size];
    }
    public boolean addPiece(int row, int col, PlayingPiece playingPiece) {
        if(row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        if(board[row][col] != null) {
            return false;
        }
        board[row][col] = playingPiece;
        return true;
    }

    public boolean isBoardFull() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(board[i][j] != null && board[i][j].getPieceType() != null) {
                    System.out.print(board[i][j].getPieceType().name() + " ");
                } else {
                    System.out.print("  ");
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

}
