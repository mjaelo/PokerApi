import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-poker-game',
  templateUrl: './poker-game.component.html',
  styleUrls: ['./poker-game.component.scss']
})
export class PokerGameComponent implements OnInit {
  @Input() login: string | undefined;

  inGame: boolean = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  showPlayer(): boolean {
    if (this.login && this.inGame) {
      return true;
    } else {
      return false;
    }
  }

  joinGame(): boolean {
    this.inGame = true;
  }

  leaveGame(): boolean {
    this.inGame = false;
  }
}
