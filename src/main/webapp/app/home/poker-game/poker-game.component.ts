import {Component, Input, OnInit} from '@angular/core';
import {GameService} from "app/entities/game/game.service";
import {PlayerService} from "app/entities/player/player.service";
import {Player} from "app/shared/model/player.model";
import {Game} from "app/shared/model/game.model";

@Component({
  selector: 'jhi-poker-game',
  templateUrl: './poker-game.component.html',
  styleUrls: ['./poker-game.component.scss']
})
export class PokerGameComponent implements OnInit {
  @Input() login: string;

  player: Player | null = new Player();
  playerId: number | undefined;
  inGame: boolean = false;
  currentGame: Game | null = new Game();
  waiting: boolean = false;

  constructor(private playerService: PlayerService, private gameService: GameService) {
  }

  ngOnInit(): void {
    this.playerService.findByNickname(this.login)
      .subscribe(
        resp => {
          this.player = resp.body;
          this.playerId = this.player?.id;
          this.isGame();
        });
  }

  isGame(): void {
    if (this.player) {
      this.gameService.findByPlayerId(this.playerId)
        .subscribe(resp => {
          this.currentGame = resp.body;
          if (this.currentGame && (this.currentGame.player1Id && this.currentGame.player2Id)) {
            this.inGame = true;
            this.waiting = false;
          }
        });
    }
  }

  showPlayer(): boolean {
    if (this.login && this.inGame) {
      return true;
    } else {
      return false;
    }
  }

  joinGame(): void {
    this.gameService.joinPlayer('' + this.playerId)
      .subscribe();
    this.waiting = true;
  }

  leaveGame(): void {
    this.waiting = false;
    this.inGame = false;
    if (this.currentGame) {
      this.gameService.delete(this.currentGame.id!).subscribe(() => {
      });
    }
  }

  showJoin(): boolean {
    if (!this.waiting && !this.inGame) {
      return true;
    } else {
      return false;
    }
  }
}
