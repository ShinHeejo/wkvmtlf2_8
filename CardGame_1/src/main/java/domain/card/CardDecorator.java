package domain.card;

public abstract class CardDecorator implements Card {
    protected final Card wrappedCard; 

    public CardDecorator(Card card) {
        this.wrappedCard = card;
    }

    @Override
    public int getNumber() { return wrappedCard.getNumber(); }

    @Override
    public int getPower() { return wrappedCard.getPower(); }

    @Override
    public int getScore() { return wrappedCard.getScore(); }

    @Override
    public String getName() { return wrappedCard.getName(); }
    
    @Override
    public String toString() { return getName(); }
}