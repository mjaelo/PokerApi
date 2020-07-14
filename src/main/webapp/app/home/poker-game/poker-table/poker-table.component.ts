import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {Card} from "app/home/poker-game/model/card.model";
import {IGame} from "app/shared/model/game.model";
import {GameService} from "app/entities/game/game.service";
import {Player} from "app/shared/model/player.model";
import {CardMap} from "app/home/poker-game/model/card.map";

@Component({
  selector: 'jhi-poker-table',
  templateUrl: './poker-table.component.html',
  styleUrls: ['./poker-table.component.scss']
})
export class PokerTableComponent implements OnInit, OnChanges {
  @Input() login: string;
  @Input() currentGame: IGame | null;
  @Input() showOppCards: boolean;

  cards: Array<number | undefined> = [56, 56, 56, 56, 56];
  opponent: Player | undefined;
  opponentCards: Array<Card> = [];
  cardMap: CardMap = new CardMap();
  jackpot: number | undefined = 0;
  opponentPot: number | undefined = 0;

  constructor(private gameService: GameService) {
  }

  ngOnInit(): void {
    this.getOpponent();
  }

  ngOnChanges(): void {
    if (this.currentGame !== undefined) {
      this.cards[0] = this.currentGame?.card1;
      this.cards[1] = this.currentGame?.card2;
      this.cards[2] = this.currentGame?.card3;
      this.cards[3] = this.currentGame?.card4;
      this.cards[4] = this.currentGame?.card5;
      this.jackpot = this.currentGame?.pot;
      if(this.showOppCards === true){
        this.showOpponentCards();
      }
      if (this.opponent?.id === this.currentGame?.player1Id) {
        this.opponentPot = this.currentGame?.p1Pot;
        if (this.opponent?.cash && this.currentGame?.player1?.cash) {
          this.opponent.cash =  this.currentGame.player1.cash;
        }
      } else {
        this.opponentPot = this.currentGame?.p2Pot;
        if (this.opponent?.cash && this.currentGame?.player2?.cash) {
          this.opponent.cash =  this.currentGame.player2.cash;
        }
      }
    }
  }

  getOpponent(): void {
    if (this.currentGame?.player1Id !== undefined) {
      if (this.login === this.currentGame.player1?.nickname) {
        this.opponent = this.currentGame?.player2;
      } else {
        this.opponent = this.currentGame?.player1;
      }
    }
    if (this.opponent) {
      let card = new Card();
      card.id = 56;
      card.name = this.cardMap.mapToSym(card.id);
      this.opponentCards.push(card);
      card = new Card();
      card.id = 56;
      card.name = this.cardMap.mapToSym(card.id);
      this.opponentCards.push(card);
    }
  }

  showOpponentCards(): void {
    if (this.opponent) {
      this.opponentCards = [];
      let card = new Card();
      card.id = this.opponent.card1!;
      card.name = this.cardMap.mapToSym(this.opponent.card1!);
      this.opponentCards.push(card);
      card = new Card();
      card.id = this.opponent.card2!;
      card.name = this.cardMap.mapToSym(this.opponent.card2!);
      this.opponentCards.push(card);
    }
  }
}
