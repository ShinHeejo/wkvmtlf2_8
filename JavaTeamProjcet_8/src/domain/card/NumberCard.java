package domain.card;

public class NumberCard implements Card {
    private final int number;

    public NumberCard(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() { return number; }

    @Override
    public int getPower() { return number; } // 기본은 숫자가 곧 전투력

    @Override
    public int getScore() { return 1; }      // 기본 점수는 1점

    @Override
    public String getName() { return "일반 [" + number + "]"; }
    
    @Override
    public String toString() { return getName(); }
}