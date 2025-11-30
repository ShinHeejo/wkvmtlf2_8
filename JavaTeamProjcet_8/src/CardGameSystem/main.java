package CardGameSystem;

import java.util.Scanner;
import dto.GameStatusDto;
import service.GameService;
import service.GameServiceImpl;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameService gameService = new GameServiceImpl(); 

        System.out.println("=== 카드 게임 테스트 ===");


        String p1Id = "User1";
        String p2Id = "Computer";
        String roomId = gameService.createRoom(p1Id);
        gameService.joinRoom(roomId, p2Id);
        System.out.println("방 생성 완료: " + roomId);
        

        while (true) {
            GameStatusDto status = gameService.getGameStatus(roomId);
            
            if ("FINISHED".equals(status.getPhase())) {
                System.out.println("\n=== 게임 종료 ===");
                System.out.println("최종 스코어 - 나: " + status.getP1Score() + ", 상대: " + status.getP2Score());
                if (status.getP1Score() > status.getP2Score()) System.out.println("나의 승리!");
                else if (status.getP1Score() < status.getP2Score()) System.out.println("상대방 승리!");
                else System.out.println("무승부!");
                break;
            }

            System.out.println("\n--------------------------------");
            System.out.println("현재 턴: " + status.getTurn() + " / 10");
            System.out.println("내 점수: " + status.getP1Score() + " vs 상대 점수: " + status.getP2Score());
            System.out.print("낼 카드 번호 입력 : ");

            try {
                int myCard = scanner.nextInt();
                
                // P1(나) 카드 제출
                gameService.playCard(roomId, p1Id, myCard);
                System.out.println("나: " + myCard + " 제출 완료.");

                int computerCard = (int)(Math.random() * 10) + 1; 

                try {
                    gameService.playCard(roomId, p2Id, computerCard);
                    System.out.println("상대: 카드를 제출했습니다.");
                } catch (Exception e) {
                    // P2가 이미 쓴 카드를 낸 경우 등
                    System.out.println("상대방(Simulated) 카드 제출 실패(이미 쓴 카드 등): " + computerCard);
                    // 테스트를 위해 강제로 진행시키진 않고 이번 턴 다시 시도해야 함
                }
                
                // 결과 확인
                GameStatusDto result = gameService.getGameStatus(roomId);
                System.out.println("결과: " + result.getRoundResult());
                
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
            }
        }
        scanner.close();
    }
}