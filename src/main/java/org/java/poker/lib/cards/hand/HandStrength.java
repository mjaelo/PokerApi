
package poker.cards.hand;

import poker.cards.card.Card;
import poker.cards.card.Rank;

public class HandStrength {
    
    
    private static final int MAIN_PREFIX            = 3200000;
    
    private static final int FIRST_KICKER_PREFIX    = 160000;
    private static final int SECOND_KICKER_PREFIX   = 8000;
    private static final int THIRD_KICKER_PREFIX    = 400;
    private static final int FOURTH_KICKER_PREFIX   = 20;
    private static final int FIFTH_KICKER_PREFIX    = 1;
    
        
    
    
    private static final int ROYAL_FLUSH    = 10 * MAIN_PREFIX;
    private static final int STRAIGHT_FLUSH = 9 * MAIN_PREFIX;
    private static final int FOUR_OF_KIND   = 8 * MAIN_PREFIX;
    private static final int FULL_HOUSE     = 7 * MAIN_PREFIX;
    private static final int FLUSH          = 6 * MAIN_PREFIX;
    private static final int STRAIGHT       = 5 * MAIN_PREFIX;
    private static final int THREE_OF_KIND  = 4 * MAIN_PREFIX;
    private static final int TWO_PAIRS      = 3 * MAIN_PREFIX;
    private static final int ONE_PAIR       = 2 * MAIN_PREFIX;
    private static final int HIGH_CARD      = 1 * MAIN_PREFIX;
    
    
    
    public static Card[] getBestHand(Hand hand){
        int strength = 0;
        Card[] bestCards = new Card[5];
        
        for(Card[] cards : HandCombinantions.getHandCombinations(hand)){
            int strengthNew = getCombinationStrength(cards);
            if(strengthNew > strength) {
                strength = strengthNew;
                bestCards = cards;
            }
        }
        return bestCards;
    }
    
    
    public static String getHandName(int handStrength){
        if(handStrength >= ROYAL_FLUSH) return "Royal Flush";
        else if(handStrength >= STRAIGHT_FLUSH) return "Straight Flush";
        else if(handStrength >= FOUR_OF_KIND) return "Four of a kind";
        else if(handStrength >= FULL_HOUSE) return "Full house";
        else if(handStrength >= FLUSH) return "Flush";
        else if(handStrength >= STRAIGHT) return "Straight";
        else if(handStrength >= THREE_OF_KIND) return "Three of a kind";
        else if(handStrength >= TWO_PAIRS) return "Two pairs";
        else if(handStrength >= ONE_PAIR) return "One pair";
        else return "High Card";
    }
    
    
    public static int getCombinationStrength(Card[] cards){
        if(isRoyalFlush(cards))         return ROYAL_FLUSH + getKickerStrength(ROYAL_FLUSH, cards);
        else if(isStraightFlush(cards)) return STRAIGHT_FLUSH + getKickerStrength(STRAIGHT_FLUSH, cards);
        else if(isFourOfKind(cards))    return FOUR_OF_KIND + getKickerStrength(FOUR_OF_KIND, cards);
        else if(isFullHouse(cards))     return FULL_HOUSE + getKickerStrength(FULL_HOUSE, cards);
        else if(isFlush(cards))         return FLUSH + getKickerStrength(FLUSH, cards);
        else if(isStraight(cards))      return STRAIGHT + getKickerStrength(STRAIGHT, cards);
        else if(isThreeOfKind(cards))   return THREE_OF_KIND + getKickerStrength(THREE_OF_KIND, cards);
        else if(isTwoPair(cards))       return TWO_PAIRS + getKickerStrength(TWO_PAIRS, cards);
        else if(isPair(cards))          return ONE_PAIR + getKickerStrength(ONE_PAIR, cards);
        else                            return HIGH_CARD + getKickerStrength(HIGH_CARD, cards);
    }
    
    
    private static int getKickerStrength(int defaultHandStrength, Card[] cards){
        switch (defaultHandStrength) {
            case ROYAL_FLUSH:
                return 0;
                
            case STRAIGHT_FLUSH:
                if(cards[0].getRank() == Rank.ACE && cards[1].getRank() == Rank.FIVE) return cards[1].getRank() * FIRST_KICKER_PREFIX;
                else return cards[0].getRank() * FIRST_KICKER_PREFIX;
                
            case FOUR_OF_KIND:
                if(cards[0].getRank() == cards[1].getRank() && cards[1].getRank() == cards[2].getRank() && cards[2].getRank() == cards[3].getRank())
                    return cards[0].getRank()* FIRST_KICKER_PREFIX + cards[4].getRank()* SECOND_KICKER_PREFIX;
                else return cards[4].getRank()* FIRST_KICKER_PREFIX + cards[0].getRank()* SECOND_KICKER_PREFIX;
                
            case FULL_HOUSE:
                if(cards[0].getRank() == cards[1].getRank() && cards[2].getRank() == cards[3].getRank() && cards[3].getRank() == cards[4].getRank())
                    return cards[4].getRank()* FIRST_KICKER_PREFIX + cards[0].getRank()* SECOND_KICKER_PREFIX;
                else return cards[0].getRank()* FIRST_KICKER_PREFIX + cards[4].getRank()* SECOND_KICKER_PREFIX;
                
            case FLUSH:
                return cards[0].getRank()* FIRST_KICKER_PREFIX + 
                       cards[1].getRank()* SECOND_KICKER_PREFIX + 
                       cards[2].getRank()* THIRD_KICKER_PREFIX + 
                       cards[3].getRank()* FOURTH_KICKER_PREFIX + 
                       cards[4].getRank()* FIFTH_KICKER_PREFIX;
                
            case STRAIGHT:
                if(cards[0].getRank() == Rank.ACE && cards[1].getRank() == Rank.FIVE) return cards[1].getRank() * FIRST_KICKER_PREFIX;
                else return cards[0].getRank() * FIRST_KICKER_PREFIX;
                
            case THREE_OF_KIND:
                if(cards[0].getRank() == cards[1].getRank() && cards[1].getRank() == cards[2].getRank())
                    return cards[0].getRank()* FIRST_KICKER_PREFIX + 
                           cards[3].getRank()* SECOND_KICKER_PREFIX + 
                           cards[4].getRank()* THIRD_KICKER_PREFIX;
                else if (cards[1].getRank() == cards[2].getRank() && cards[2].getRank() == cards[3].getRank())
                    return cards[1].getRank()* FIRST_KICKER_PREFIX + 
                           cards[0].getRank()* SECOND_KICKER_PREFIX + 
                           cards[4].getRank()* THIRD_KICKER_PREFIX;
                else 
                    return cards[2].getRank()* FIRST_KICKER_PREFIX + 
                           cards[0].getRank()* SECOND_KICKER_PREFIX + 
                           cards[1].getRank()* THIRD_KICKER_PREFIX;
                
            case TWO_PAIRS:
                if(cards[0].getRank() == cards[1].getRank() && cards[2].getRank() == cards[3].getRank())
                    return cards[0].getRank()* FIRST_KICKER_PREFIX + 
                           cards[2].getRank()* SECOND_KICKER_PREFIX + 
                           cards[4].getRank()* THIRD_KICKER_PREFIX;
                else if (cards[0].getRank() == cards[1].getRank() && cards[3].getRank() == cards[4].getRank())
                    return cards[0].getRank()* FIRST_KICKER_PREFIX + 
                           cards[3].getRank()* SECOND_KICKER_PREFIX + 
                           cards[2].getRank()* THIRD_KICKER_PREFIX;
                else 
                    return cards[1].getRank()* FIRST_KICKER_PREFIX + 
                           cards[3].getRank()* SECOND_KICKER_PREFIX + 
                           cards[0].getRank()* THIRD_KICKER_PREFIX;
                
            case ONE_PAIR:
                if(cards[0].getRank() == cards[1].getRank())
                    return cards[0].getRank()* FIRST_KICKER_PREFIX + 
                           cards[2].getRank()* SECOND_KICKER_PREFIX + 
                           cards[3].getRank()* THIRD_KICKER_PREFIX +
                           cards[4].getRank()* FOURTH_KICKER_PREFIX;
                else if(cards[1].getRank() == cards[2].getRank())
                    return cards[1].getRank()* FIRST_KICKER_PREFIX + 
                           cards[0].getRank()* SECOND_KICKER_PREFIX + 
                           cards[3].getRank()* THIRD_KICKER_PREFIX +
                           cards[4].getRank()* FOURTH_KICKER_PREFIX;
                else if(cards[2].getRank() == cards[3].getRank())
                    return cards[2].getRank()* FIRST_KICKER_PREFIX + 
                           cards[0].getRank()* SECOND_KICKER_PREFIX + 
                           cards[1].getRank()* THIRD_KICKER_PREFIX +
                           cards[4].getRank()* FOURTH_KICKER_PREFIX;
                else
                    return cards[3].getRank()* FIRST_KICKER_PREFIX + 
                           cards[0].getRank()* SECOND_KICKER_PREFIX + 
                           cards[1].getRank()* THIRD_KICKER_PREFIX +
                           cards[2].getRank()* FOURTH_KICKER_PREFIX;
                
            case HIGH_CARD:
                return cards[0].getRank()* FIRST_KICKER_PREFIX + 
                       cards[1].getRank()* SECOND_KICKER_PREFIX + 
                       cards[2].getRank()* THIRD_KICKER_PREFIX + 
                       cards[3].getRank()* FOURTH_KICKER_PREFIX + 
                       cards[4].getRank()* FIFTH_KICKER_PREFIX;
                
            default:
                return -ROYAL_FLUSH-1;
        }
    }
    
    
    private static boolean isRoyalFlush(Card[] cards){
        return isStraightFlush(cards) && cards[0].getRank()==Rank.ACE && cards[1].getRank()==Rank.KING;
    }
    
    private static boolean isStraightFlush(Card[] cards){
        return isStraight(cards) && isFlush(cards);
    }
    
    private static boolean isFourOfKind(Card[] cards){
        return (
                cards[0].getRank() == cards[1].getRank() && 
                cards[1].getRank() == cards[2].getRank() && 
                cards[2].getRank() == cards[3].getRank())
                || (
                cards[1].getRank() == cards[2].getRank() && 
                cards[2].getRank() == cards[3].getRank() && 
                cards[3].getRank() == cards[4].getRank()
                );
    }
    
    private static boolean isFullHouse(Card[] cards){
        return (
                cards[0].getRank() == cards[1].getRank() && 
                cards[2].getRank() == cards[3].getRank() && 
                cards[3].getRank() == cards[4].getRank())
                || (
                cards[0].getRank() == cards[1].getRank() && 
                cards[1].getRank() == cards[2].getRank() && 
                cards[3].getRank() == cards[4].getRank()
                );
    }
    
    private static boolean isFlush(Card[] cards){
        return (
                cards[0].getColor() == cards[1].getColor() && 
                cards[1].getColor() == cards[2].getColor() && 
                cards[2].getColor() == cards[3].getColor() && 
                cards[3].getColor() == cards[4].getColor()
                );
    }
    
    private static boolean isStraight(Card[] cards){
        if(cards[0].getRank() == Rank.ACE &&
           cards[1].getRank() == Rank.FIVE &&
           cards[2].getRank() == Rank.FOUR &&
           cards[3].getRank() == Rank.THREE &&
           cards[4].getRank() == Rank.TWO) return true;
        else return (
           cards[0].getRank() == cards[1].getRank()+1 &&
           cards[1].getRank() == cards[2].getRank()+1 &&
           cards[2].getRank() == cards[3].getRank()+1 &&
           cards[3].getRank() == cards[4].getRank()+1);
    }
    
    private static boolean isThreeOfKind(Card[] cards){
        return (
           cards[0].getRank() == cards[1].getRank() &&
           cards[1].getRank() == cards[2].getRank())
           || (
           cards[1].getRank() == cards[2].getRank() &&
           cards[2].getRank() == cards[3].getRank())
           || (
           cards[2].getRank() == cards[3].getRank() &&
           cards[3].getRank() == cards[4].getRank());
    }
    
    private static boolean isTwoPair(Card[] cards){
        return (
           cards[0].getRank() == cards[1].getRank() &&
           cards[2].getRank() == cards[3].getRank())
           || (
           cards[0].getRank() == cards[1].getRank() &&
           cards[3].getRank() == cards[4].getRank())
           || (
           cards[1].getRank() == cards[2].getRank() &&
           cards[3].getRank() == cards[4].getRank());                
    }
    
    private static boolean isPair(Card[] cards){
        return (
                cards[0].getRank() == cards[1].getRank() || 
                cards[1].getRank() == cards[2].getRank() || 
                cards[2].getRank() == cards[3].getRank() || 
                cards[3].getRank() == cards[4].getRank());               
    }
    
    
}
