package service;

import domain.GameRoom;
import domain.Player;
import domain.card.Card;
import dto.GameStatusDto;
import repository.GameRepository;
import org.springframework.stereotype.Service; // 추가됨
import java.util.ArrayList;
import java.util.List;

@Service // [Spring] 서비스 빈(Bean) 등록
public class GameServiceImpl implements GameService {

    private final GameRepository repository;

    // [Spring] 생성자 주입 (DI): 내가 new 하지 않고 Spring이 넣어줌
    public GameServiceImpl(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createRoom(String player1Id) {
        // 실제로는 UUID 등을 사용하여 고유 ID를 생성하는 것이 좋음
        // 여기서는 테스트를 위해 고정하거나, 팀원과 협의하여 ID 생성 로직 결정
        String roomId = "ROOM_" + System.currentTimeMillis(); 
        Player p1 = new Player(player1Id);
        GameRoom room = new GameRoom(roomId, p1);
        repository.save(room);
        return roomId;
    }

    @Override
    public boolean joinRoom(String roomId, String player2Id) {
        GameRoom room = repository.findById(roomId);
        if (room == null || room.isFull()) return false;
        
        // 이미 있는 플레이어인지 체크하는 로직이 필요할 수도 있음
        room.join(new Player(player2Id));
        return true;
    }

    @Override
    public GameStatusDto playCard(String roomId, String playerId, int cardNumber) {
        GameRoom room = repository.findById(roomId);
        if (room == null) throw new RuntimeException("방을 찾을 수 없습니다."); // 예외 처리 강화

        Player player = null;
        if (room.getP1().getPlayerId().equals(playerId)) player = room.getP1();
        else if (room.getP2() != null && room.getP2().getPlayerId().equals(playerId)) player = room.getP2();
        
        if (player == null) throw new RuntimeException("플레이어 없음");

        player.playCard(cardNumber);

        if (room.isRoundReady()) {
            room.resolveTurn();
        }

        return convertToDto(room);
    }

    @Override
    public GameStatusDto getGameStatus(String roomId) {
        GameRoom room = repository.findById(roomId);
        if (room == null) throw new RuntimeException("방을 찾을 수 없습니다.");
        return convertToDto(room);
    }

    // DTO 변환 로직 (기존과 동일)
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
        
        List<String> deckNames = new ArrayList<>();
        // P1 시점에서 보여줄 덱 (웹소켓 연결 시 본인 구분이 필요하지만, 일단 P1 기준으로 작성됨)
        // *주의*: 실제 서버에서는 요청한 사람이 P1인지 P2인지에 따라 deckList를 다르게 줘야 함.
        // 현재 로직은 로직 검증용이므로 그대로 두되, 팀원에게 "이 부분은 요청자 ID에 따라 내 덱을 리턴하게 수정해"라고 말하면 됨.
        for (Card c : room.getP1().getDeck()) {
            deckNames.add(c.getName());
        }
        dto.setMyDeckList(deckNames);
        
        if (room.isGameOver()) dto.setPhase("FINISHED");
        else dto.setPhase("PLAYING");
        
        return dto;
    }
}