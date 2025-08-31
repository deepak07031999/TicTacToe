package org.deepak.dto;

public interface GameObserver {
    void onGameEnd(Player winner, Player player1, Player player2);
}