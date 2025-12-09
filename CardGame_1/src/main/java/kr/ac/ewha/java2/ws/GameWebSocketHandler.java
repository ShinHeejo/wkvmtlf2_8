package kr.ac.ewha.java2.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.GameStatusDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import service.GameService;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final GameService gameService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String roomId = null;
    private String player1Id = null;
    private String player2Id = null;

    private final Set<WebSocketSession> sessions =
            ConcurrentHashMap.newKeySet();
    private final Map<WebSocketSession, String> playerIds =
            new ConcurrentHashMap<>();

    private final Set<String> playedThisTurn =
            ConcurrentHashMap.newKeySet();

    public GameWebSocketHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        playerIds.remove(session);

        if (sessions.isEmpty()) {
            roomId = null;
            player1Id = null;
            player2Id = null;
            playedThisTurn.clear();
        }

        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        GameMessage msg = objectMapper.readValue(message.getPayload(), GameMessage.class);

        switch (msg.getType()) {
            case "JOIN":
                handleJoin(session, msg);
                break;
            case "PLAY":
                handlePlay(session, msg);
                break;
            default:
                sendError(session, "unknown_type");
        }
    }

    private void handleJoin(WebSocketSession session, GameMessage msg) throws IOException {
        if (sessions.size() >= 2) {
            sendError(session, "room_full");
            session.close();
            return;
        }

        String playerId = msg.getPlayerId();

        if (sessions.isEmpty()) {
            roomId = gameService.createRoom(playerId);
            player1Id = playerId;
        } else {
            gameService.joinRoom(roomId, playerId);
            player2Id = playerId;
        }

        sessions.add(session);
        playerIds.put(session, playerId);

        if (sessions.size() == 2) {
            GameStatusDto status = gameService.getGameStatus(roomId);
            broadcastStatus(status);
        }
    }

    private void handlePlay(WebSocketSession session, GameMessage msg) throws IOException {
        if (roomId == null) {
            sendError(session, "game_not_ready");
            return;
        }

        String playerId = playerIds.get(session);
        if (playerId == null) {
            sendError(session, "not_joined");
            return;
        }

        try {
            gameService.playCard(roomId, playerId, msg.getCardNumber());
        } catch (Exception e) {
            sendError(session, "invalid_card");
            return;
        }

        GameStatusDto status = gameService.getGameStatus(roomId);

        broadcastStatus(status);
    }

    private void broadcastStatus(GameStatusDto status) throws IOException {
        for (WebSocketSession s : sessions) {
            if (!s.isOpen()) continue;

            String pid = playerIds.get(s);
            if (pid == null) continue;

            WsStatusResponse view = new WsStatusResponse();

            if (pid.equals(player1Id)) {
                // 나는 P1
                view.setMeName(player1Id);
                view.setOpponentName(player2Id);

                view.setMyScore(status.getP1Score());
                view.setOpponentScore(status.getP2Score());

                view.setMyCardName(status.getP1CardName());
                view.setOpponentCardName(status.getP2CardName());

                view.setMeIsP1(true);  

            } else {

                view.setMeName(player2Id);
                view.setOpponentName(player1Id);

                view.setMyScore(status.getP2Score());
                view.setOpponentScore(status.getP1Score());

                view.setMyCardName(status.getP2CardName());
                view.setOpponentCardName(status.getP1CardName());

                view.setMeIsP1(false); 
            }


            view.setTurn(status.getTurn());
            view.setPhase(status.getPhase());
            view.setRoundResult(status.getRoundResult());
            view.setMyDeckList(gameService.getDeckNames(roomId, pid));

            String json = objectMapper.writeValueAsString(view);
            s.sendMessage(new TextMessage(json));
        }
    }




    private void sendError(WebSocketSession session, String code) throws IOException {
        String json = "{\"error\":\"" + code + "\"}";
        session.sendMessage(new TextMessage(json));
    }
}
