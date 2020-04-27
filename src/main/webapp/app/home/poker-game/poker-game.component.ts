import {Component, Input, OnInit} from '@angular/core';
import {GameService} from "app/entities/game/game.service";
import {PlayerService} from "app/entities/player/player.service";
import {Player} from "app/shared/model/player.model";

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

  constructor(private playerService: PlayerService, private gameService: GameService) {
  }

  ngOnInit(): void {
    this.playerService.findByNickname(this.login)
      .subscribe(
        resp => {
          this.player = resp.body;
          this.playerId = this.player?.id;
        });
  }

  showPlayer(): boolean {
    if (this.login && this.inGame) {
      return true;
    } else {
      return false;
    }
  }

  joinGame(): void {
    this.gameService.joinPlayer(this.playerId as string)
      .subscribe();
    console.warn(this.playerId);
    this.isGame();
  }

  leaveGame(): void {
    this.inGame = false;
  }

  isGame(): void {
    do {
      console.warn(true);
    } while (this.inGame);
  }
}
