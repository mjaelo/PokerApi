import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PokerSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { PokerGameComponent } from './poker-game/poker-game.component';
import { PokerPlayerComponent } from './poker-game/poker-player/poker-player.component';
import { PokerCardComponent } from './poker-game/poker-card/poker-card.component';

@NgModule({
  imports: [PokerSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, PokerGameComponent, PokerPlayerComponent, PokerCardComponent]
})
export class PokerHomeModule {}
