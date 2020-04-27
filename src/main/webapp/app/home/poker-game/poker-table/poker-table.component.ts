import {Component, OnInit} from '@angular/core';
import {Card} from "app/home/poker-game/model/card.model";

@Component({
  selector: 'jhi-poker-table',
  templateUrl: './poker-table.component.html',
  styleUrls: ['./poker-table.component.scss']
})
export class PokerTableComponent implements OnInit {

  cards: Array<Card> = [];

  constructor() {
  }

  ngOnInit(): void {
    this.setSampleCards();
  }

  setSampleCards(): void {
    let card = new Card();
    card.id = 1;
    card.name = '2H';
    this.cards.push(card);

    card = new Card();
    card.id = 2;
    card.name = 'QH';
    this.cards.push(card);

    card = new Card();
    card.id = 3;
    card.name = '10H';
    this.cards.push(card);

    card = new Card();
    card.id = 4;
    card.name='purple_back';
    this.cards.push(card);


    card = new Card();
    card.id = 5;
    card.name='purple_back';
    this.cards.push(card);
  }

}
