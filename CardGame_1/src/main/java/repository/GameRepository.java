package repository;

import domain.GameRoom;
import org.springframework.stereotype.Repository; 
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; 

@Repository 
public class GameRepository {

    private final Map<String, GameRoom> storage = new ConcurrentHashMap<>();

    public void save(GameRoom room) {
        storage.put(room.getRoomId(), room);
    }

    public GameRoom findById(String roomId) {
        return storage.get(roomId);
    }
    
    public void remove(String roomId) {
        storage.remove(roomId);
    }
}