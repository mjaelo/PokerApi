import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-poker-game',
  templateUrl: './poker-game.component.html',
  styleUrls: ['./poker-game.component.scss']
})
export class PokerGameComponent implements OnInit {
  @Input() login: string | undefined;

  constructor() {
  }

  ngOnInit(): void {
  }

}
