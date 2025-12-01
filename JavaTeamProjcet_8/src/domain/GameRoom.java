package domain;

import domain.card.Card;

public class GameRoom {
    private String roomId;
    private Player p1;
    private Player p2;
    private int turn = 1;
    private boolean isGameOver = false;
    private String lastRoundMessage = "게임 대기 중...";

    public GameRoom(String roomId, Player p1) {
        this.roomId = roomId;
        this.p1 = p1;
    }

    public void join(Player p2) { this.p2 = p2; }
    
    public boolean isFull() { return p1 != null && p2 != null; }

    public boolean isRoundReady() {
        return p1.getCurrentCard() != null && p2.getCurrentCard() != null;
    }

    // [핵심 변경] 숫자 비교 -> 객체의 getPower(), getScore() 사용
    public void resolveTurn() {
        if (!isRoundReady()) return;

        Card c1 = p1.getCurrentCard();
        Card c2 = p2.getCurrentCard();

        int power1 = c1.getPower();
        int power2 = c2.getPower();

        StringBuilder sb = new StringBuilder();
        // 결과 메시지에 카드 이름(속성 포함)을 표시
        sb.append(String.format("결과: P1 %s vs P2 %s -> ", c1.getName(), c2.getName()));

        if (power1 > power2) {
            int scoreGain = c1.getScore(); // 이긴 카드의 점수 획득량
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

        if (this.turn > 10) isGameOver = true;
    }

    public String getRoomId() { return roomId; }
    public Player getP1() { return p1; }
    public Player getP2() { return p2; }
    public int getTurn() { return turn; }
    public boolean isGameOver() { return isGameOver; }
    public String getLastRoundMessage() { return lastRoundMessage; }
}