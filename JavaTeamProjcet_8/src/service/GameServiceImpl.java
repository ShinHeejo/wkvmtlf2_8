package service;

import domain.GameRoom;
import domain.Player;
import dto.GameStatusDto;
import repository.GameRepository;

public class GameServiceImpl implements GameService {

    private final GameRepository repository = new GameRepository();

    @Override
    public String createRoom(String player1Id) {
        String roomId = "ROOM_001"; // 예시로 고정 ID 사용 (나중엔 UUID 사용)
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

    @Override
    public GameStatusDto playCard(String roomId, String playerId, int cardNum) {
        GameRoom room = repository.findById(roomId);
        Player player = null;
        
        // 플레이어 찾기
        if (room.getP1().getPlayerId().equals(playerId)) player = room.getP1();
        else if (room.getP2().getPlayerId().equals(playerId)) player = room.getP2();
        
        if (player == null) throw new RuntimeException("플레이어 없음");

        ///[cite_start]// 1. 카드 내기 (도메인 객체에게 위임) [cite: 1] (2, 3번 항목)
        player.playCard(cardNum);

        // 2. 두 명 다 냈으면 턴 결과 처리 (도메인 객체에게 위임)
        if (room.isRoundReady()) {
            room.resolveTurn(); // 승패 판정 및 턴 증가
        }

        // 3. 현재 상태를 DTO로 변환하여 반환
        return convertToDto(room);
    }

    @Override
    public GameStatusDto getGameStatus(String roomId) {
        GameRoom room = repository.findById(roomId);
        return convertToDto(room);
    }

    // 도메인 객체를 DTO로 변환하는 메서드
    private GameStatusDto convertToDto(GameRoom room) {
        GameStatusDto dto = new GameStatusDto();
        dto.setTurn(room.getTurn());
        dto.setP1Score(room.getP1().getScore());
        dto.setRoundResult(room.getLastRoundMessage());
        
        // P2가 아직 입장 안 했을 경우 처리
        if (room.getP2() != null) {
            dto.setP2Score(room.getP2().getScore());
        }
        
        if (room.isGameOver()) {
            dto.setPhase("FINISHED");
        } else {
            dto.setPhase("PLAYING");
        }
        return dto;
    }
}
