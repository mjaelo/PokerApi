import {Component, Input, OnInit} from '@angular/core';
import {Player} from "app/shared/model/player.model";
import {PlayerService} from "app/entities/player/player.service";
import {Game} from "app/shared/model/game.model";
import {Card} from "app/home/poker-game/model/card.model";
import {CardMap} from "app/home/poker-game/model/card.map";

@Component({
  selector: 'jhi-poker-player',
  templateUrl: './poker-player.component.html',
  styleUrls: ['./poker-player.component.scss']
})
export class PokerPlayerComponent implements OnInit {
  @Input() login: string;
  @Input() currentGame: Game;

  player: Player | null;
  gameId: number;
  cards: Array<Card> = [];
  cardMap: CardMap = new CardMap();


  constructor(private playerService: PlayerService) {
  }

  ngOnInit(): void {
    this.playerService.findByNickname(this.login)
      .subscribe(
        resp => {
          this.player = resp.body;
          this.setCards();
        });
  }

  setCards(): void {
    if (this.player) {
      let card = new Card();
      this.player.card1 = 42;
      card.id = this.player.card1;
      card.name = this.cardMap.mapToSym(this.player.card1);
      console.warn(card);
      this.cards.push(card);
      card = new Card();
      this.player.card2 = 33;
      card.id = this.player.card2;
      card.name = this.cardMap.mapToSym(this.player.card2);
      this.cards.push(card);
    }
  }
}
