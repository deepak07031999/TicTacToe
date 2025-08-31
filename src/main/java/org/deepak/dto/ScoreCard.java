package org.deepak.dto;

import javafx.util.Pair;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

@Data
public class ScoreCard implements GameObserver {
    private static final Logger logger = LoggerFactory.getLogger(ScoreCard.class);
    private final HashMap<String, Pair<Integer,Integer>> playerScoreCard = new HashMap<>();

    public Pair<Integer,Integer> getCurrentScore(Player player1, Player player2) {
        String key = getKey(player1, player2);
        Pair<Integer,Integer> score = playerScoreCard.getOrDefault(key, new Pair<>(0, 0));
        
        String name1 = player1.getPlayerName();
        String name2 = player2.getPlayerName();
        boolean isPlayer1First = name1.compareTo(name2) < 0;
        
        return isPlayer1First ? score : new Pair<>(score.getValue(), score.getKey());
    }
    
@Override
    public void onGameEnd(Player winner, Player player1, Player player2) {
        String key = getKey(player1, player2);
        Pair<Integer,Integer> currentScore = playerScoreCard.getOrDefault(key, new Pair<>(0, 0));
        
        String name1 = player1.getPlayerName();
        String name2 = player2.getPlayerName();
        boolean isPlayer1First = name1.compareTo(name2) < 0;
        
        if ((isPlayer1First && winner.equals(player1)) || (!isPlayer1First && winner.equals(player2))) {
            playerScoreCard.put(key, new Pair<>(currentScore.getKey() + 1, currentScore.getValue()));
        } else {
            playerScoreCard.put(key, new Pair<>(currentScore.getKey(), currentScore.getValue() + 1));
        }
        
        logger.info("Score updated for {}: {} - {}: {}", 
                   player1.getPlayerName(), getCurrentScore(player1, player2).getKey(),
                   player2.getPlayerName(), getCurrentScore(player1, player2).getValue());
    }
    
    private String getKey(Player player1, Player player2) {
        String name1 = player1.getPlayerName();
        String name2 = player2.getPlayerName();
        return name1.compareTo(name2) < 0 ? name1 + "_vs_" + name2 : name2 + "_vs_" + name1;
    }
}
