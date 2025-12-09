package kr.ac.ewha.java2.api;

public class PlayRequest {
    private String playerId;
    private int cardNumber;

    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public int getCardNumber() { return cardNumber; }
    public void setCardNumber(int cardNumber) { this.cardNumber = cardNumber; }
}
