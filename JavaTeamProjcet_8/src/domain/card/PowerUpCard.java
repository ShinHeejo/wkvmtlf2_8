package domain.card;

// OCP 적용: 기존 코드를 수정하지 않고 기능(전투력 증가)을 추가함
public class PowerUpCard extends CardDecorator {
    public PowerUpCard(Card card) {
        super(card);
    }

    @Override
    public int getPower() {
        return super.getPower() + 2; // 전투력 +2
    }

    @Override
    public String getName() {
        return "⚔️공격형 [" + super.getNumber() + "]";
    }
}