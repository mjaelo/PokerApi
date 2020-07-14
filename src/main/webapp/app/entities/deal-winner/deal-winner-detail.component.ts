import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDealWinner } from 'app/shared/model/deal-winner.model';

@Component({
  selector: 'jhi-deal-winner-detail',
  templateUrl: './deal-winner-detail.component.html'
})
export class DealWinnerDetailComponent implements OnInit {
  dealWinner: IDealWinner | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealWinner }) => (this.dealWinner = dealWinner));
  }

  previousState(): void {
    window.history.back();
  }
}
