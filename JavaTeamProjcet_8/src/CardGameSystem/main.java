package CardGameSystem;

import dto.GameStatusDto;
import service.GameService;
import service.GameServiceImpl;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameService gameService = new GameServiceImpl();

        String p1Id = "User1";
        String p2Id = "Computer";
        String roomId = gameService.createRoom(p1Id);
        gameService.joinRoom(roomId, p2Id);
        
        System.out.println("=== 카드 게임 시작 ===");
        System.out.println("카드 종류: 일반 카드, 숫자+2 카드, 점수 2배 카드\n");

        while (true) {
            GameStatusDto status = gameService.getGameStatus(roomId);

            if ("FINISHED".equals(status.getPhase())) {
                System.out.println("\n=== 게임 종료 ===");
                System.out.printf("최종 스코어 - 나: %d vs 상대: %d\n", status.getP1Score(), status.getP2Score());
                break;
            }

            System.out.println("\n---------------- TURN " + status.getTurn() + " ----------------");
            System.out.printf("점수 현황 - [나: %d점] vs [상대: %d점]\n", status.getP1Score(), status.getP2Score());
            
            // [추가] 내 패 목록 보여주기
            System.out.println("\n[ 나의 패 목록 ]");
            if (status.getMyDeckList() != null) {
                for (String cardName : status.getMyDeckList()) {
                    System.out.print(cardName + " | ");
                }
            }
            System.out.println(); // 줄바꿈

            // 내 차례 입력
            boolean p1Success = false;
            while (!p1Success) {
                System.out.print("\n낼 카드의 '숫자(값)'를 입력하세요: ");
                try {
                    int num = scanner.nextInt();
                    // 인덱스 변환 없이 숫자 그대로 전달
                    gameService.playCard(roomId, p1Id, num);
                    System.out.println(">> 나: " + num + "번 카드 제출");
                    p1Success = true;
                } catch (Exception e) {
                    System.out.println("❌ 오류: 없는 숫자거나 이미 낸 카드입니다.");
                }
            }

            // 상대(컴퓨터) 차례
            boolean p2Success = false;
            while (!p2Success) {
                try {
                    // 컴퓨터는 랜덤으로 1~10 숫자 중 하나를 시도
                    int randomNum = (int)(Math.random() * 10) + 1;
                    gameService.playCard(roomId, p2Id, randomNum);
                    System.out.println(">> 상대: 카드를 제출했습니다.");
                    p2Success = true;
                } catch (Exception e) {
                    // 이미 낸 숫자면 다시 루프
                }
            }

            // 결과 확인
            status = gameService.getGameStatus(roomId);
            System.out.println("📢 " + status.getRoundResult());
        }
        scanner.close();
    }
}