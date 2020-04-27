import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-poker-card',
  templateUrl: './poker-card.component.html',
  styleUrls: ['./poker-card.component.scss']
})
export class PokerCardComponent implements OnInit {
  @Input() cardName: string;

  card: string;

  constructor() {}

  ngOnInit(): void {
    this.fun();
  }

  fun(): void {
    this.card = '../../../../content/images/cards-pictures/'+this.cardName+'.png';
  }
}
