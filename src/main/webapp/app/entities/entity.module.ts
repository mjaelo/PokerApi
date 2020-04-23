import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'player',
        loadChildren: () => import('./player/player.module').then(m => m.PokerPlayerModule)
      },
      {
        path: 'game',
        loadChildren: () => import('./game/game.module').then(m => m.PokerGameModule)
      },
      {
        path: 'deal-winner',
        loadChildren: () => import('./deal-winner/deal-winner.module').then(m => m.PokerDealWinnerModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PokerEntityModule {}
