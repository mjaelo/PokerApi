import {Component, Input, OnInit} from '@angular/core';
import {Card} from "app/home/poker-game/model/card.model";
import {Game} from "app/shared/model/game.model";
import {GameService} from "app/entities/game/game.service";
import {Player} from "app/shared/model/player.model";
import {CardMap} from "app/home/poker-game/model/card.map";

@Component({
  selector: 'jhi-poker-table',
  templateUrl: './poker-table.component.html',
  styleUrls: ['./poker-table.component.scss']
})
export class PokerTableComponent implements OnInit {
  @Input() login: string;
  @Input() currentGame: Game;

  cards: Array<Card> = [];
  opponent: Player | undefined;
  opponentCards: Array<Card> = [];
  cardMap: CardMap = new CardMap();

  constructor(private gameService: GameService) {
  }

  ngOnInit(): void {
    this.getOpponent();
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
    card.name = 'purple_back';
    this.cards.push(card);

    card = new Card();
    card.id = 5;
    card.name = 'purple_back';
    this.cards.push(card);
  }

  getOpponent(): void {
    if (this.login === this.currentGame.player1?.nickname) {
      this.opponent = this.currentGame?.player2;
    } else {
      this.opponent = this.currentGame?.player1;
    }
    if (this.opponent) {
      let card = new Card();
      this.opponent.card1 = 56;
      card.id = this.opponent.card1;
      card.name = this.cardMap.mapToSym(this.opponent.card1);
      this.opponentCards.push(card);
      card = new Card();
      this.opponent.card2 = 56;
      card.id = this.opponent.card2;
      card.name = this.cardMap.mapToSym(this.opponent.card2);
      this.opponentCards.push(card);
    }
  }

  showOpponentCards(): void {
    if (this.opponent) {
      this.opponentCards = [];
      let card = new Card();
      this.opponent.card1 = 23;
      card.id = this.opponent.card1;
      card.name = this.cardMap.mapToSym(this.opponent.card1);
      this.opponentCards.push(card);
      card = new Card();
      this.opponent.card2 = 17;
      card.id = this.opponent.card2;
      card.name = this.cardMap.mapToSym(this.opponent.card2);
      this.opponentCards.push(card);
    }
  }
}
