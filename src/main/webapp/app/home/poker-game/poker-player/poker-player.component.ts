import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {Player} from "app/shared/model/player.model";
import {PlayerService} from "app/entities/player/player.service";
import {IGame} from "app/shared/model/game.model";
import {Card} from "app/home/poker-game/model/card.model";
import {CardMap} from "app/home/poker-game/model/card.map";
import {GameService} from "app/entities/game/game.service";
import {Observable} from "rxjs";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'jhi-poker-player',
  templateUrl: './poker-player.component.html',
  styleUrls: ['./poker-player.component.scss']
})
export class PokerPlayerComponent implements OnInit, OnChanges {
  @Input() login: string;
  @Input() currentGame: IGame;

  player: Player | null;
  gameId: number;
  cards: Array<Card> = [];
  cardMap: CardMap = new CardMap();
  raiseSum: number = 0;
  looser: string = 'looser';
  playerJackpot: number = 0;

  constructor(private playerService: PlayerService, private gameService: GameService) {
  }

  ngOnInit(): void {
    this.playerService.findByNickname(this.login)
      .subscribe(
        resp => {
          this.player = resp.body;
          this.setCards();
        });
  }

  ngOnChanges(): void {
    if (this.player?.id === this.currentGame.player1Id) {
      if (this.currentGame.p1Pot) {
        this.playerJackpot = this.currentGame.p1Pot;
      }
    }
    if (this.player?.id === this.currentGame.player2Id) {
      if (this.currentGame.p2Pot) {
        this.playerJackpot = this.currentGame.p2Pot;
      }
    }
  }

  setCards(): void {
    if (this.player) {
      let card = new Card();
      card.id = this.player.card1!;
      card.name = this.cardMap.mapToSym(this.player.card1!);
      this.cards.push(card);
      card = new Card();
      card.id = this.player.card2!;
      card.name = this.cardMap.mapToSym(this.player.card2!);
      this.cards.push(card);
    }
  }

  raisePot(): void {
    if (this.player?.cash) {
      this.player.cash -= this.raiseSum;
    }


    if (this.raiseSum > 0) {
      this.gameService.raise(this.currentGame.id, this.player?.id, this.raiseSum).subscribe(resp => console.warn(resp));
    }
  }

  fold(): void {
    this.gameService.fold(this.currentGame.id, this.player?.id)
      .subscribe(resp => {
        this.looser = resp.result;
        alert(this.looser);
      });
  }

  check(): void {
    this.gameService.check(this.currentGame.id, this.player?.id).subscribe(resp => console.warn(resp));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>): void {
    result.subscribe();
  }

// updateGame(): void {
//   if (this.currentGame.id !== undefined) {
//     this.gameService.find(this.currentGame.id)
//       .subscribe(resp => {
//         this.currentGame = resp.body;
//         console.warn(this.currentGame);
//       });
//   }
// }

  getAndUpdateGame(): void {
  }

  saveGame(): void {
    if (this.currentGame.id !== undefined
    ) {
      this.subscribeToSaveResponse(this.gameService.update(this.currentGame));
    } else {
      this.subscribeToSaveResponse(this.gameService.create(this.currentGame));
    }
  }
}
