package domain.card;

public interface Card {
    int getNumber();      // 카드의 기본 숫자
    int getPower();       // 전투력 (버프 적용됨)
    int getScore();       // 승리 시 획득 점수 (버프 적용됨)
    String getName();     // 카드 이름 (예: "⚔️공격형 [7]")
}