package dto;

public class GameStatusDto {
    private String roomId;
    private int turn;           // 현재 턴 수
    private String phase;       // "WAITING"(대기중), "PLAYING"(진행중), "FINISHED"(게임종료)
    private String roundResult; // 이번 턴 결과 메시지 (예: "P1 승리!")
    private int p1Card;         // P1이 낸 카드 (공개 전에는 0)
    private int p2Card;         // P2가 낸 카드
    private int p1Score;
    private int p2Score;
    
    // 생성자, Getter, Setter, toString
    public GameStatusDto() {}
    
    // 편의를 위해 모든 필드를 받는 생성자 대신 Builder 패턴이나 Setter 사용 권장하지만
    // 여기선 간단히 Setter만 생성하셔도 됩니다.
    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public int getTurn() { return turn; }
    public void setTurn(int turn) { this.turn = turn; }
    public String getRoundResult() { return roundResult; }
    public void setRoundResult(String roundResult) { this.roundResult = roundResult; }
    public int getP1Score() { return p1Score; }
    public void setP1Score(int p1Score) { this.p1Score = p1Score; }
    public int getP2Score() { return p2Score; }
    public void setP2Score(int p2Score) { this.p2Score = p2Score; }
    public int getP1Card() { return p1Card; }
    public void setP1Card(int p1Card) { this.p1Card = p1Card; }
    public int getP2Card() { return p2Card; }
    public void setP2Card(int p2Card) { this.p2Card = p2Card; }
}