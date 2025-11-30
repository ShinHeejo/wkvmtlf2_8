package domain;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String playerId;
    private int score;
    private List<Integer> deck;
    private Integer currentCard; // 이번 턴에 낸 카드 (안 냈으면 null)

    public Player(String playerId) {
        this.playerId = playerId;
        this.score = 0;
        this.deck = new ArrayList<>();
        // 1부터 10까지 덱 생성
        for (int i = 1; i <= 10; i++) {
            deck.add(i);
        }
    }

    // 카드를 내는 행위 (검증 포함)
    public void playCard(int cardNum) {
        if (!deck.contains(cardNum)) {
            throw new IllegalArgumentException("보유하지 않은 카드입니다.");
        }
        this.deck.remove(Integer.valueOf(cardNum)); // 덱에서 삭제
        this.currentCard = cardNum;
    }
    
    public void resetCurrentCard() {
        this.currentCard = null;
    }

    public void addScore() {
        this.score++;
    }

    // Getter
    public String getPlayerId() { return playerId; }
    public int getScore() { return score; }
    public List<Integer> getDeck() { return deck; }
    public Integer getCurrentCard() { return currentCard; }
}