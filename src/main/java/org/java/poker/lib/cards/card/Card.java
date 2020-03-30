package org.java.poker.lib.cards.card;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Card implements Comparable<Card> {

    private final int color;
    private final int rank;

    public Card(int color, int rank) {
        this.color = color;
        this.rank = rank;
    }

    public int getColor() {
        return color;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
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
