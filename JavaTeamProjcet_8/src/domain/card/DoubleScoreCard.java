package domain.card;

// OCP 적용: 기존 코드를 수정하지 않고 기능(점수 증가)을 추가함
public class DoubleScoreCard extends CardDecorator {
    public DoubleScoreCard(Card card) {
        super(card);
    }

    @Override
    public int getScore() {
        return super.getScore() * 2; // 점수 2배
    }

    @Override
    public String getName() {
        return "💎대박형 [" + super.getNumber() + "]";
    }
}