package kr.ac.ewha.java2.api;

import dto.GameStatusDto;
import org.springframework.web.bind.annotation.*;
import service.GameService;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/rooms")
    public GameStatusDto startGame(@RequestBody CreateRoomRequest request) {
        String p1Id = request.getPlayerId();
        String p2Id = "Computer";

        String roomId = gameService.createRoom(p1Id);
        gameService.joinRoom(roomId, p2Id);

        GameStatusDto status = gameService.getGameStatus(roomId);
        status.setRoomId(roomId); 
        return status;
    }


    @PostMapping("/rooms/{roomId}/play")
    public GameStatusDto play(@PathVariable String roomId,
                              @RequestBody PlayRequest request) {

        String p1Id = request.getPlayerId();
        String p2Id = "Computer";


        gameService.playCard(roomId, p1Id, request.getCardNumber());


        boolean computerPlayed = false;
        while (!computerPlayed) {
            int randomNum = (int)(Math.random() * 10) + 1;
            try {
                gameService.playCard(roomId, p2Id, randomNum);
                computerPlayed = true;
            } catch (Exception e) {

            }
        }


        GameStatusDto status = gameService.getGameStatus(roomId);
        status.setRoomId(roomId);
        return status;
    }


    @GetMapping("/rooms/{roomId}/status")
    public GameStatusDto getStatus(@PathVariable String roomId) {
        GameStatusDto status = gameService.getGameStatus(roomId);
        status.setRoomId(roomId);
        return status;
    }
}
