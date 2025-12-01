package domain;

import domain.card.Card;
import domain.factory.DeckFactory;
import java.util.List;

public class Player {
    private String playerId;
    private int score;
    private List<Card> deck;
    private Card currentCard;

    public Player(String playerId) {
        this.playerId = playerId;
        this.score = 0;
        this.deck = DeckFactory.createBalancedDeck();
    }

    // [수정] 인덱스 대신 '카드 숫자'로 찾아서 제출
    public void playCard(int cardNumber) {
        Card foundCard = null;
        
        // 덱에서 해당 숫자를 가진 카드 찾기
        for (Card c : deck) {
            if (c.getNumber() == cardNumber) {
                foundCard = c;
                break;
            }
        }

        if (foundCard == null) {
            throw new IllegalArgumentException("해당 숫자의 카드가 없습니다.");
        }

        // 덱에서 제거하고 현재 카드로 설정
        deck.remove(foundCard);
        this.currentCard = foundCard;
    }
    
    public void resetCurrentCard() {
        this.currentCard = null;
    }

    public void addScore(int points) {
        this.score += points;
    }

    // Getters
    public String getPlayerId() { return playerId; }
    public int getScore() { return score; }
    public List<Card> getDeck() { return deck; }
    public Card getCurrentCard() { return currentCard; }
}