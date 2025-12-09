package domain.card;

public class NumberCard implements Card {
    private final int number;

    public NumberCard(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() { return number; }

    @Override
    public int getPower() { return number; } 

    @Override
    public int getScore() { return 1; }  

    @Override
    public String getName() { return "일반 [" + number + "]"; }
    
    @Override
    public String toString() { return getName(); }
}