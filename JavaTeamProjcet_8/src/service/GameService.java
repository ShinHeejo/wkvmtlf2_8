package service;

import dto.GameStatusDto;

public interface GameService {
    String createRoom(String player1Id);
    boolean joinRoom(String roomId, String player2Id);
    GameStatusDto playCard(String roomId, String playerId, int cardNum);
    GameStatusDto getGameStatus(String roomId); // 현재 상태 조회용
}
