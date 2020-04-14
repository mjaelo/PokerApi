import { IUser } from 'app/core/user/user.model';

export interface IPlayer {
  id?: number;
  nickname?: string;
  cash?: number;
  user?: IUser;
}

export class Player implements IPlayer {
  constructor(public id?: number, public nickname?: string, public cash?: number, public user?: IUser) {}
}
