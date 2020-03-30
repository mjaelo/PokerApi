
package org.java.poker.lib.cards.hand;

import lombok.Data;
import org.java.poker.lib.cards.card.Card;

@Data
public class Hand {

    private Card[] handCards = new Card[2];
    private Card[] tableCards = new Card[5];

    public Hand(Card[] handCards, Card[] tableCards) {
        this.handCards = handCards;
        this.tableCards = tableCards;
    }

    public Card[] getHandCards() {
        return handCards;
    }

    public void setHandCards(Card[] handCards) {
        this.handCards = handCards;
    }

    public Card[] getTableCards() {
        return tableCards;
    }

    public void setTableCards(Card[] tableCards) {
        this.tableCards = tableCards;
    }

    public Card[] getAllCards(){
        Card[] allCards = new Card[7];

        allCards[0] = getHandCards()[0];
        allCards[1] = getHandCards()[1];
        allCards[2] = getTableCards()[0];
        allCards[3] = getTableCards()[1];
        allCards[4] = getTableCards()[2];
        allCards[5] = getTableCards()[3];
        allCards[6] = getTableCards()[4];

        return allCards;
    }
}
