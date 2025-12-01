package dto;

import java.util.List;

public class GameStatusDto {
    private String roomId;
    private int turn;
    private String phase;
    private String roundResult;
    
    private String p1CardName;  
    private String p2CardName;
    
    private int p1Score;
    private int p2Score;
    
    // [추가] 내 패 목록 (이름으로 저장)
    private List<String> myDeckList;
    
    public GameStatusDto() {}
    
    // Getters and Setters
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public int getTurn() { return turn; }
    public void setTurn(int turn) { this.turn = turn; }
    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public String getRoundResult() { return roundResult; }
    public void setRoundResult(String roundResult) { this.roundResult = roundResult; }
    
    public String getP1CardName() { return p1CardName; }
    public void setP1CardName(String p1CardName) { this.p1CardName = p1CardName; }
    public String getP2CardName() { return p2CardName; }
    public void setP2CardName(String p2CardName) { this.p2CardName = p2CardName; }
    
    public int getP1Score() { return p1Score; }
    public void setP1Score(int p1Score) { this.p1Score = p1Score; }
    public int getP2Score() { return p2Score; }
    public void setP2Score(int p2Score) { this.p2Score = p2Score; }
    
    public List<String> getMyDeckList() { return myDeckList; }
    public void setMyDeckList(List<String> myDeckList) { this.myDeckList = myDeckList; }
}