import { IUser } from 'app/core/user/user.model';

export interface IPlayer {
  id?: number;
  nickname?: string;
  cash?: number;
  card1?: number;
  card2?: number;
  user?: IUser;
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public nickname?: string,
    public cash?: number,
    public card1?: number,
    public card2?: number,
    public user?: IUser
  ) {}
}
