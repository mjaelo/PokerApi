import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDealWinner } from 'app/shared/model/deal-winner.model';
import { DealWinnerService } from './deal-winner.service';
import { DealWinnerDeleteDialogComponent } from './deal-winner-delete-dialog.component';

@Component({
  selector: 'jhi-deal-winner',
  templateUrl: './deal-winner.component.html'
})
export class DealWinnerComponent implements OnInit, OnDestroy {
  dealWinners?: IDealWinner[];
  eventSubscriber?: Subscription;

  constructor(protected dealWinnerService: DealWinnerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.dealWinnerService.query().subscribe((res: HttpResponse<IDealWinner[]>) => (this.dealWinners = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDealWinners();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDealWinner): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDealWinners(): void {
    this.eventSubscriber = this.eventManager.subscribe('dealWinnerListModification', () => this.loadAll());
  }

  delete(dealWinner: IDealWinner): void {
    const modalRef = this.modalService.open(DealWinnerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dealWinner = dealWinner;
  }
}
