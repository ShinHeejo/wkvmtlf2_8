package repository;

import domain.GameRoom;
import java.util.HashMap;
import java.util.Map;

public class GameRepository {
    // 메모리에 게임 방들을 저장 (DB 대용)
    private static Map<String, GameRoom> storage = new HashMap<>();

    public void save(GameRoom room) {
        storage.put(room.getRoomId(), room);
    }

    public GameRoom findById(String roomId) {
        return storage.get(roomId);
    }
}