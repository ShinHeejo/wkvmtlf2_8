package repository;

import domain.GameRoom;
import org.springframework.stereotype.Repository; // 추가됨
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; // 추가됨

@Repository // [Spring] 저장소 빈(Bean) 등록
public class GameRepository {
    // [중요] 웹 환경(멀티스레드) 충돌 방지를 위해 ConcurrentHashMap 사용
    // static을 떼도 Spring이 싱글톤으로 관리하므로 데이터는 유지됨
    private final Map<String, GameRoom> storage = new ConcurrentHashMap<>();

    public void save(GameRoom room) {
        storage.put(room.getRoomId(), room);
    }

    public GameRoom findById(String roomId) {
        return storage.get(roomId);
    }
    
    // (선택) 방 삭제 기능이 필요하다면 추가
    public void remove(String roomId) {
        storage.remove(roomId);
    }
}