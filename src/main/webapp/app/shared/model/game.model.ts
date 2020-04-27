import { IPlayer } from 'app/shared/model/player.model';

export interface IGame {
  id?: number;
  card1?: number;
  card2?: number;
  card3?: number;
  card4?: number;
  card5?: number;
  player1Id?: number;
  player2Id?: number;
  player2?: IPlayer;
  player1?: IPlayer;
}

export class Game implements IGame {
  constructor(
    public id?: number,
    public card1?: number,
    public card2?: number,
    public card3?: number,
    public card4?: number,
    public card5?: number,
    public player1Id?: number,
    public player2Id?: number,
    public player2?: IPlayer,
    public player1?: IPlayer
  ) {}
}
