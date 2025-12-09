package kr.ac.ewha.java2.ws;

public class GameMessage {

    private String type;
    private String playerId;
    private Integer cardNumber; 

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public Integer getCardNumber() { return cardNumber; }
    public void setCardNumber(Integer cardNumber) { this.cardNumber = cardNumber; }
}
