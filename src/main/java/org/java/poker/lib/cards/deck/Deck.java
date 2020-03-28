
package poker.cards.deck;

import poker.cards.card.Card;
import poker.cards.card.Color;
import poker.cards.card.Rank;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    // Talia kart
    ArrayList<Card> deck;
    
    // Konstruktor ustawiający nową talię oraz tasujący
    public Deck(){
        deck = new ArrayList<>();
        
        for(int color=Color.RANGE_COLOR_MIN; color<=Color.RANGE_COLOR_MAX; color++){
            for(int rank=Rank.RANGE_RANK_MIN; rank<=Rank.RANGE_RANK_MAX; rank++){
                deck.add(new Card(color,rank));
            }
        }   
        
        shuffle();
    }
    
    public void shuffle() {
        Collections.shuffle(deck);
    }
    
    public Card takeCard(){
        int card = (int)(System.currentTimeMillis() % deck.size());
        Card ret = deck.get(card);
        deck.remove(card);
        return ret;
    }
}
