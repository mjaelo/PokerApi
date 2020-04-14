import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-poker-card',
  templateUrl: './poker-card.component.html',
  styleUrls: ['./poker-card.component.scss']
})
export class PokerCardComponent implements OnInit {
  nazwa: string;

  constructor() {}

  ngOnInit(): void {}

  fun(nazwa2: string): void {
    this.nazwa = nazwa2;
  }
  fun2(): string {
    return 'green_back.png';
  }
}
