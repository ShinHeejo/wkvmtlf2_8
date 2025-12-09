package domain.card;

public class PowerUpCard extends CardDecorator {
    public PowerUpCard(Card card) {
        super(card);
    }

    @Override
    public int getPower() {
        return super.getPower() + 2; 
    }

    @Override
    public String getName() {
        return "카드 강화! 숫자 +2 [" + super.getNumber() + "]";
    }
}