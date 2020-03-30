package org.java.poker.web.rest;


import org.java.poker.lib.cards.card.Card;
import org.java.poker.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardResource {

    private final CardService cardService;

    public CardResource(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public Card getCard(){
        return this.cardService.getCard();
    }
}
