package domain.factory;

import domain.card.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckFactory {
    
    public static List<Card> createBalancedDeck() {
        List<Card> deck = new ArrayList<>();
        
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        int powerUpTarget = numbers.get(0);      
        int doubleScoreTarget = numbers.get(1); 

        for (int i = 1; i <= 10; i++) {
            Card card = new NumberCard(i);

            if (i == powerUpTarget) {
                card = new PowerUpCard(card);
            } else if (i == doubleScoreTarget) {
                card = new DoubleScoreCard(card);
            }

            deck.add(card);
        }

        return deck;
    }
}