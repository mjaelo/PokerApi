import {Component, Input, OnInit} from '@angular/core';
import {Player} from "app/shared/model/player.model";
import {PlayerService} from "app/entities/player/player.service";
import {SERVER_API_URL} from "app/app.constants";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'jhi-poker-player',
  templateUrl: './poker-player.component.html',
  styleUrls: ['./poker-player.component.scss']
})
export class PokerPlayerComponent implements OnInit {
  @Input() login: string;

  public resourceUrl = SERVER_API_URL + 'api/games';
  public joinUrl = this.resourceUrl + '/join-player';

  player: Player | null = new Player();
  playerId: number;
  gameId: Number;
  card1 = '2C';
  card2 = '10H';
  isJoined: String = 'Not work';


  constructor(private playerService: PlayerService, private http: HttpClient) {
  }

  ngOnInit(): void {
    this.playerService.findByNickname(this.login)
      .subscribe(
        resp => {
          this.player = resp.body;
          // this.playerId = this.player?.id;
        });
  }

  join():void{
    this.isJoined = this.joinGame();
    console.warn(this.isJoined);
  }

  // joinGame(): Observable<String> {
  //   this.joinUrl = this.joinUrl + "/" + this.player?.id;
  //   console.warn(this.joinUrl);
  //   return this.http.get<String>(this.joinUrl, {observe: 'response'});
  // }

  // findByNickname(nickname: String): Observable<String> {
  //   return this.http.get<IPlayer>(`${this.resourceUrl}/nickname/${nickname}`, {observe: 'response'});
  // }

  // joinGame(): Observable<EntityResponseType> {
  // console.warn(this.playerId);
  // this.joinPlayer(this.playerId);
  // return this.http.post(this.joinUrl, this.player, {observe: 'response'});
  // }
  //
  // create(game: IGame): Observable<EntityResponseType> {
  //   return this.http.post<IGame>(this.resourceUrl, game, { observe: 'response' });
  // }

  //
  // joinPlayer(id: number): void {
  //   console.warn(id);
  //   // this.http.post<any>(this.joinUrl, id);
  //   console.warn(this.http);
  //   this.http.get<any>(`${this.resourceUrl}/${id}`,{ observe: 'response' });
  // }


}
