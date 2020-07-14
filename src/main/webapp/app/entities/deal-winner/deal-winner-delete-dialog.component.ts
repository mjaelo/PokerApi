import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDealWinner } from 'app/shared/model/deal-winner.model';
import { DealWinnerService } from './deal-winner.service';

@Component({
  templateUrl: './deal-winner-delete-dialog.component.html'
})
export class DealWinnerDeleteDialogComponent {
  dealWinner?: IDealWinner;

  constructor(
    protected dealWinnerService: DealWinnerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dealWinnerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dealWinnerListModification');
      this.activeModal.close();
    });
  }
}
