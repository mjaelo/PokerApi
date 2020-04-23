import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokerSharedModule } from 'app/shared/shared.module';
import { DealWinnerComponent } from './deal-winner.component';
import { DealWinnerDetailComponent } from './deal-winner-detail.component';
import { DealWinnerUpdateComponent } from './deal-winner-update.component';
import { DealWinnerDeleteDialogComponent } from './deal-winner-delete-dialog.component';
import { dealWinnerRoute } from './deal-winner.route';

@NgModule({
  imports: [PokerSharedModule, RouterModule.forChild(dealWinnerRoute)],
  declarations: [DealWinnerComponent, DealWinnerDetailComponent, DealWinnerUpdateComponent, DealWinnerDeleteDialogComponent],
  entryComponents: [DealWinnerDeleteDialogComponent]
})
export class PokerDealWinnerModule {}
