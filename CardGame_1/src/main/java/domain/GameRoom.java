package domain;

import domain.card.Card;

public class GameRoom {
    private String roomId;
    private Player p1;
    private Player p2;
    private int turn = 1;
    private boolean isGameOver = false;
    private String lastRoundMessage = "게임 대기 중...";

    private String lastP1CardName;
    private String lastP2CardName;

    public String getLastP1CardName() {
        return lastP1CardName;
    }

    public String getLastP2CardName() {
        return lastP2CardName;
    }

    public GameRoom(String roomId, Player p1) {
        this.roomId = roomId;
        this.p1 = p1;
    }

    public void join(Player p2) { this.p2 = p2; }
    
    public boolean isFull() { return p1 != null && p2 != null; }

    public boolean isRoundReady() {
        return p1.getCurrentCard() != null && p2.getCurrentCard() != null;
    }

    public void resolveTurn() {
        if (!isRoundReady()) return;

        Card c1 = p1.getCurrentCard();
        Card c2 = p2.getCurrentCard();

        this.lastP1CardName = c1.getName();
        this.lastP2CardName = c2.getName();

        int power1 = c1.getPower();
        int power2 = c2.getPower();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("결과: P1 %s vs P2 %s -> ", c1.getName(), c2.getName()));

        if (power1 > power2) {
            int scoreGain = c1.getScore();
            p1.addScore(scoreGain);
            sb.append("P1 승리! (+" + scoreGain + "점)");
        } else if (power1 < power2) {
            int scoreGain = c2.getScore();
            p2.addScore(scoreGain);
            sb.append("P2 승리! (+" + scoreGain + "점)");
        } else {
            sb.append("무승부");
        }

        this.lastRoundMessage = sb.toString();

        p1.resetCurrentCard();
        p2.resetCurrentCard();
        this.turn++;

        if (this.turn > 10) {
            isGameOver = true;
            sb.append("\n\n [최종 결과] "); 
            
            if (p1.getScore() > p2.getScore()) {
                sb.append("P1 플레이어 최종 우승!");
            } else if (p1.getScore() < p2.getScore()) {
                sb.append("P2 플레이어 최종 우승!");
            } else {
                sb.append("최종 무승부!");
            }
        }

        this.lastRoundMessage = sb.toString();
    }


    public String getRoomId() { return roomId; }
    public Player getP1() { return p1; }
    public Player getP2() { return p2; }
    public int getTurn() { return turn; }
    public boolean isGameOver() { return isGameOver; }
    public String getLastRoundMessage() { return lastRoundMessage; }
}