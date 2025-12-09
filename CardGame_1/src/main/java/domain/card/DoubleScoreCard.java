package domain.card;

public class DoubleScoreCard extends CardDecorator {
    public DoubleScoreCard(Card card) {
        super(card);
    }

    @Override
    public int getScore() {
        return super.getScore() * 2; 
    }

    @Override
    public String getName() {
        return "점수 2배! [" + super.getNumber() + "]";
    }
}