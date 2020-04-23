import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-poker-card',
  templateUrl: './poker-card.component.html',
  styleUrls: ['./poker-card.component.scss']
})
export class PokerCardComponent implements OnInit {

  nazwa: string;

  constructor() {}

  ngOnInit(): void {
    this.fun();
  }

  fun(): void {
    this.nazwa = '../../../../content/images/cards-pictures/2H.png';
  }
}
