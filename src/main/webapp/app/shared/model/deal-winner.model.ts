import { IPlayer } from 'app/shared/model/player.model';
import { IGame } from 'app/shared/model/game.model';

export interface IDealWinner {
  id?: number;
  winner?: IPlayer;
  game?: IGame;
}

export class DealWinner implements IDealWinner {
  constructor(public id?: number, public winner?: IPlayer, public game?: IGame) {}
}
