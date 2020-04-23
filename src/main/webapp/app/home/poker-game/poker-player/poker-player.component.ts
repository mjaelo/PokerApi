import {Component, Input, OnInit} from '@angular/core';
import {Player} from "app/shared/model/player.model";
import {PlayerService} from "app/entities/player/player.service";

@Component({
  selector: 'jhi-poker-player',
  templateUrl: './poker-player.component.html',
  styleUrls: ['./poker-player.component.scss']
})
export class PokerPlayerComponent implements OnInit {
  @Input() login: string;

  player: Player | null = new Player();
  gameId: Number;

  constructor(private playerService: PlayerService) {
  }

  ngOnInit(): void {
    this.playerService.findByNickname(this.login)
      .subscribe(resp => (this.player = resp.body));
    this.findGame();
  }

  findGame(): void {

  }

}
