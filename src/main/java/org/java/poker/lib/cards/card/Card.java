
package org.java.poker.lib.cards.card;


public class Card implements Comparable<Card>{

    private final int color;
    private final int rank;

    public Card(int color, int rank) {
        this.color = color;
        this.rank = rank;
    }

    public static Card getCard(long cardId){
        switch((int)cardId){
            case 1: return new Card(Color.CLUB,Rank.TWO);
            case 2: return new Card(Color.DIAMOND,Rank.TWO);
            case 3: return new Card(Color.HEART,Rank.TWO);
            case 4: return new Card(Color.SPADE,Rank.TWO);

            case 5: return new Card(Color.CLUB,Rank.THREE);
            case 6: return new Card(Color.DIAMOND,Rank.THREE);
            case 7: return new Card(Color.HEART,Rank.THREE);
            case 8: return new Card(Color.SPADE,Rank.THREE);

            case 9: return new Card(Color.CLUB,Rank.FOUR);
            case 10: return new Card(Color.DIAMOND,Rank.FOUR);
            case 11: return new Card(Color.HEART,Rank.FOUR);
            case 12: return new Card(Color.SPADE,Rank.FOUR);

            case 13: return new Card(Color.CLUB,Rank.FIVE);
            case 14: return new Card(Color.DIAMOND,Rank.FIVE);
            case 15: return new Card(Color.HEART,Rank.FIVE);
            case 16: return new Card(Color.SPADE,Rank.FIVE);

            case 17: return new Card(Color.CLUB,Rank.SIX);
            case 18: return new Card(Color.DIAMOND,Rank.SIX);
            case 19: return new Card(Color.HEART,Rank.SIX);
            case 20: return new Card(Color.SPADE,Rank.SIX);

            case 21: return new Card(Color.CLUB,Rank.SEVEN);
            case 22: return new Card(Color.DIAMOND,Rank.SEVEN);
            case 23: return new Card(Color.HEART,Rank.SEVEN);
            case 24: return new Card(Color.SPADE,Rank.SEVEN);

            case 25: return new Card(Color.CLUB,Rank.EIGHT);
            case 26: return new Card(Color.DIAMOND,Rank.EIGHT);
            case 27: return new Card(Color.HEART,Rank.EIGHT);
            case 28: return new Card(Color.SPADE,Rank.EIGHT);

            case 29: return new Card(Color.CLUB,Rank.NINE);
            case 30: return new Card(Color.DIAMOND,Rank.NINE);
            case 31: return new Card(Color.HEART,Rank.NINE);
            case 32: return new Card(Color.SPADE,Rank.NINE);

            case 33: return new Card(Color.CLUB,Rank.TEN);
            case 34: return new Card(Color.DIAMOND,Rank.TEN);
            case 35: return new Card(Color.HEART,Rank.TEN);
            case 36: return new Card(Color.SPADE,Rank.TEN);

            case 37: return new Card(Color.CLUB,Rank.JACK);
            case 38: return new Card(Color.DIAMOND,Rank.JACK);
            case 39: return new Card(Color.HEART,Rank.JACK);
            case 40: return new Card(Color.SPADE,Rank.JACK);

            case 41: return new Card(Color.CLUB,Rank.QUEEN);
            case 42: return new Card(Color.DIAMOND,Rank.QUEEN);
            case 43: return new Card(Color.HEART,Rank.QUEEN);
            case 44: return new Card(Color.SPADE,Rank.QUEEN);

            case 45: return new Card(Color.CLUB,Rank.KING);
            case 46: return new Card(Color.DIAMOND,Rank.KING);
            case 47: return new Card(Color.HEART,Rank.KING);
            case 48: return new Card(Color.SPADE,Rank.KING);

            case 49: return new Card(Color.CLUB,Rank.ACE);
            case 50: return new Card(Color.DIAMOND,Rank.ACE);
            case 51: return new Card(Color.HEART,Rank.ACE);
            case 52: return new Card(Color.SPADE,Rank.ACE);

            default: return null;
        }
    }

    public int getColor() {
        return color;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString(){
        String rankStr, colorStr;

        switch (this.rank) {
            case Rank.ACE:
                rankStr = "A";
                break;
            case Rank.KING:
                rankStr = "K";
                break;
            case Rank.QUEEN:
                rankStr = "Q";
                break;
            case Rank.JACK:
                rankStr = "J";
                break;
            default:
                rankStr = String.valueOf(this.rank);
                break;
        }

        switch (this.color) {
            case Color.HEART:
                colorStr = "♡";
                break;
            case Color.DIAMOND:
                colorStr = "♢";
                break;
            case Color.CLUB:
                colorStr = "♧";
                break;
            case Color.SPADE:
                colorStr = "♤";
                break;
            default:
                colorStr = "Błąd";
                break;
        }

        return rankStr + colorStr;
    }

    @Override
    public int compareTo(Card o) {
        return getRank() < o.getRank()? 1: -1;
    }
}
