package domain;

public class GameRoom {
    private String roomId;
    private Player p1;
    private Player p2;
    private int turn = 1; // 1턴부터 시작
    private boolean isGameOver = false;
    private String lastRoundMessage = "게임 시작";

    public GameRoom(String roomId, Player p1) {
        this.roomId = roomId;
        this.p1 = p1;
    }

    public void join(Player p2) {
        this.p2 = p2;
    }
    
    // 두 명이 다 찼는지 확인
    public boolean isFull() {
        return p1 != null && p2 != null;
    }

    //[cite_start]// 두 명이 모두 카드를 냈는지 확인 [cite: 1] (4번 항목)
    public boolean isRoundReady() {
        return p1.getCurrentCard() != null && p2.getCurrentCard() != null;
    }

    //[cite_start]// 턴 결과 계산 (승패 판정) [cite: 1] (5, 6, 7번 항목)
    public void resolveTurn() {
        if (!isRoundReady()) return;

        int c1 = p1.getCurrentCard();
        int c2 = p2.getCurrentCard();

        if (c1 > c2) {
            p1.addScore();
            this.lastRoundMessage = "P1 승리 (" + c1 + " vs " + c2 + ")";
        } else if (c1 < c2) {
            p2.addScore();
            this.lastRoundMessage = "P2 승리 (" + c1 + " vs " + c2 + ")";
        } else {
            this.lastRoundMessage = "무승부 (" + c1 + " vs " + c2 + ")";
        }

        // 라운드 정리
        p1.resetCurrentCard();
        p2.resetCurrentCard();
        this.turn++;

        //[cite_start]// 게임 종료 조건 확인 (10턴 초과 시) [cite: 1] (0번 항목)
        if (this.turn > 10) {
            this.isGameOver = true;
            this.lastRoundMessage = "게임 종료! 최종 스코어 확인";
        }
    }

    // Getter
    public String getRoomId() { return roomId; }
    public Player getP1() { return p1; }
    public Player getP2() { return p2; }
    public int getTurn() { return turn; }
    public boolean isGameOver() { return isGameOver; }
    public String getLastRoundMessage() { return lastRoundMessage; }
}