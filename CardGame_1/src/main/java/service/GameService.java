package service;

import java.util.List;

import dto.GameStatusDto;

public interface GameService {
    String createRoom(String player1Id);
    void joinRoom(String roomId, String player2Id);
    void playCard(String roomId, String playerId, int cardNumber);
    GameStatusDto getGameStatus(String roomId); 
    List<String> getDeckNames(String roomId, String playerId);
}
