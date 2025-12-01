package service;

import domain.GameRoom;
import domain.Player;
import domain.card.Card;
import dto.GameStatusDto;
import repository.GameRepository;
import java.util.ArrayList;
import java.util.List;

public class GameServiceImpl implements GameService {

    private final GameRepository repository = new GameRepository();

    @Override
    public String createRoom(String player1Id) {
        String roomId = "ROOM_001"; 
        Player p1 = new Player(player1Id);
        GameRoom room = new GameRoom(roomId, p1);
        repository.save(room);
        return roomId;
    }

    @Override
    public boolean joinRoom(String roomId, String player2Id) {
        GameRoom room = repository.findById(roomId);
        if (room == null || room.isFull()) return false;
        room.join(new Player(player2Id));
        return true;
    }

    // [수정] cardIndex -> cardNumber (숫자 값 자체를 받음)
    @Override
    public GameStatusDto playCard(String roomId, String playerId, int cardNumber) {
        GameRoom room = repository.findById(roomId);
        Player player = null;
        
        if (room.getP1().getPlayerId().equals(playerId)) player = room.getP1();
        else if (room.getP2().getPlayerId().equals(playerId)) player = room.getP2();
        
        if (player == null) throw new RuntimeException("플레이어 없음");

        // [수정] 숫자 기반으로 카드 제출
        player.playCard(cardNumber);

        if (room.isRoundReady()) {
            room.resolveTurn();
        }

        return convertToDto(room);
    }

    @Override
    public GameStatusDto getGameStatus(String roomId) {
        GameRoom room = repository.findById(roomId);
        return convertToDto(room);
    }

    private GameStatusDto convertToDto(GameRoom room) {
        GameStatusDto dto = new GameStatusDto();
        dto.setRoomId(room.getRoomId());
        dto.setTurn(room.getTurn());
        dto.setP1Score(room.getP1().getScore());
        dto.setRoundResult(room.getLastRoundMessage());
        
        if (room.getP1().getCurrentCard() != null) {
            dto.setP1CardName(room.getP1().getCurrentCard().getName());
        }
        
        if (room.getP2() != null) {
            dto.setP2Score(room.getP2().getScore());
            if (room.getP2().getCurrentCard() != null) {
                dto.setP2CardName(room.getP2().getCurrentCard().getName());
            }
        }
        
        // [추가] P1의 덱 목록을 이름(String) 리스트로 변환해서 전달
        List<String> deckNames = new ArrayList<>();
        for (Card c : room.getP1().getDeck()) {
            deckNames.add(c.getName());
        }
        dto.setMyDeckList(deckNames);
        
        if (room.isGameOver()) dto.setPhase("FINISHED");
        else dto.setPhase("PLAYING");
        
        return dto;
    }
}