package kr.ac.ewha.java2.api;

public class JoinRoomRequest {
    private String playerId;
    private String roomId;

    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
}