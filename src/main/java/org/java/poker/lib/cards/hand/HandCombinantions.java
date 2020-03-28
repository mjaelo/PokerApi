
package poker.cards.hand;

import java.util.ArrayList;
import java.util.Arrays;
import poker.cards.card.Card;

public class HandCombinantions extends Combinations {
    
    public static ArrayList<Card[]> getHandCombinations(Hand hand){
        ArrayList<Card[]> ret = new ArrayList<>();
        
        Card[] allCards = hand.getAllCards();
                
        for(int[] i : combinations){
            Card[] combination = new Card[5];
            for(int j=0; j<5; j++){
                combination[j] = allCards[i[j]];
            }
            Arrays.sort(combination);
            ret.add(combination);
        }
        
        return ret;
    }
    
}
