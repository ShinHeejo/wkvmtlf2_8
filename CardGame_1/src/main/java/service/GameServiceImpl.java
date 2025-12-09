package service;

import domain.GameRoom;
import domain.Player;
import domain.card.Card;
import dto.GameStatusDto;
import org.springframework.stereotype.Service;
import repository.GameRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public String createRoom(String playerId) {
        String roomId = UUID.randomUUID().toString();
        Player p1 = new Player(playerId);
        GameRoom room = new GameRoom(roomId, p1);
        gameRepository.save(room);
        return roomId;
    }

    @Override
    public void joinRoom(String roomId, String playerId) {
        GameRoom room = getRoomOrThrow(roomId);

        if (room.isFull()) {
            throw new IllegalStateException("이미 두 명이 참가한 방입니다.");
        }

        Player p2 = new Player(playerId);
        room.join(p2);

        gameRepository.save(room);
    }

    @Override
    public void playCard(String roomId, String playerId, int cardNumber) {
        GameRoom room = getRoomOrThrow(roomId);

        Player targetPlayer = findPlayerInRoom(room, playerId);
        targetPlayer.playCard(cardNumber);

        if (room.isRoundReady()) {
            room.resolveTurn();
        }

        gameRepository.save(room);
    }

    @Override
    public GameStatusDto getGameStatus(String roomId) {
        GameRoom room = getRoomOrThrow(roomId);

        GameStatusDto dto = new GameStatusDto();
        dto.setRoomId(room.getRoomId());
        dto.setTurn(room.getTurn());

        String phase;
        if (!room.isFull()) {
            phase = "WAITING";      
        } else if (room.isGameOver()) {
            phase = "FINISHED";      
        } else {
            phase = "PLAYING";      
        }
        dto.setPhase(phase);

        dto.setRoundResult(room.getLastRoundMessage());

        if (room.getP1() != null) {
            dto.setP1Score(room.getP1().getScore());
        } else {
            dto.setP1Score(0);
        }
        if (room.getP2() != null) {
            dto.setP2Score(room.getP2().getScore());
        } else {
            dto.setP2Score(0);
        }

        dto.setP1CardName(room.getLastP1CardName());
        dto.setP2CardName(room.getLastP2CardName());

        if (room.getP1() != null) {
            List<String> deckNames = room.getP1().getDeck().stream()
                    .map(Card::getName)
                    .collect(Collectors.toList());
            dto.setMyDeckList(deckNames);
        } else {
            dto.setMyDeckList(Collections.emptyList());
        }

        return dto;
    }

    @Override
    public List<String> getDeckNames(String roomId, String playerId) {
        GameRoom room = getRoomOrThrow(roomId);
        Player p = findPlayerInRoom(room, playerId);

        return p.getDeck().stream()
                .map(Card::getName) 
                .collect(Collectors.toList());
    }


    private GameRoom getRoomOrThrow(String roomId) {
        GameRoom room = gameRepository.findById(roomId);
        if (room == null) {
            throw new IllegalArgumentException("방이 존재하지 않습니다: " + roomId);
        }
        return room;
    }

    private Player findPlayerInRoom(GameRoom room, String playerId) {
        if (room.getP1() != null && room.getP1().getPlayerId().equals(playerId)) {
            return room.getP1();
        }
        if (room.getP2() != null && room.getP2().getPlayerId().equals(playerId)) {
            return room.getP2();
        }
        throw new IllegalArgumentException("플레이어가 방에 없습니다: " + playerId);
    }
}
