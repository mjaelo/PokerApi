package org.java.poker.service;

import org.java.poker.lib.cards.card.Card;
import org.springframework.stereotype.Service;


@Service
public class CardService {

    public Card getCard(){
        return new Card(1,1);
    }

}
