package domain.factory;

import domain.card.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckFactory {
    
    // [수정] 확률이 아니라, '공격형 1장', '대박형 1장'을 확정적으로 포함시킴
    public static List<Card> createBalancedDeck() {
        List<Card> deck = new ArrayList<>();
        
        // 1. 1부터 10까지의 숫자 리스트를 만듦
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        // 2. 숫자를 섞어서, 앞에서 두 개의 숫자를 뽑음 (버프를 줄 카드 번호 선정)
        Collections.shuffle(numbers);
        int powerUpTarget = numbers.get(0);      // 공격형으로 만들 숫자
        int doubleScoreTarget = numbers.get(1);  // 대박형으로 만들 숫자

        // 3. 1부터 10까지 카드를 생성하면서 버프 부여
        for (int i = 1; i <= 10; i++) {
            Card card = new NumberCard(i);

            if (i == powerUpTarget) {
                // 아까 뽑은 첫 번째 숫자라면 '공격형' 장착
                card = new PowerUpCard(card);
            } else if (i == doubleScoreTarget) {
                // 아까 뽑은 두 번째 숫자라면 '대박형' 장착
                card = new DoubleScoreCard(card);
            }
            // 그 외에는 그냥 일반 카드로 둠

            deck.add(card);
        }

        // 4. (선택) 덱을 섞어서 플레이어에게 지급하려면 shuffle 사용
        // 만약 1부터 10까지 정렬된 상태로 주고 싶다면 아래 줄을 주석 처리하세요.
        // Collections.shuffle(deck); 
        
        // 지금은 플레이어가 보기 편하게 '정렬된 상태(1~10)'로 주되,
        // 어떤 숫자에 버프가 붙었는지만 랜덤인 상태로 리턴하겠습니다.
        return deck;
    }
}