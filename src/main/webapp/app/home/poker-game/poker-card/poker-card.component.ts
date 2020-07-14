import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {CardMap} from "app/home/poker-game/model/card.map";

@Component({
  selector: 'jhi-poker-card',
  templateUrl: './poker-card.component.html',
  styleUrls: ['./poker-card.component.scss']
})
export class PokerCardComponent implements OnInit, OnChanges{
  @Input() cardId: number;

  card: string;
  cardMap: CardMap = new CardMap();

  constructor() {}

  ngOnInit(): void {
    this.map();
  }

  ngOnChanges(): void {
    this.map();
  }

  map(): void {
    this.card = '../../../../content/images/cards-pictures/'+this.cardMap.mapToSym(this.cardId)+'.png';
  }
}
