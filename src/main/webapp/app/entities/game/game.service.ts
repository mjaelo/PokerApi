import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import {createRequestOption} from 'app/shared/util/request-util';
import {IGame} from 'app/shared/model/game.model';

type EntityResponseType = HttpResponse<IGame>;
type EntityArrayResponseType = HttpResponse<IGame[]>;

@Injectable({ providedIn: 'root' })
export class GameService {
  public resourceUrl = SERVER_API_URL + 'api/games';
  public joinUrl = SERVER_API_URL + 'api/join-player/';

  constructor(protected http: HttpClient) {}

  create(game: IGame): Observable<EntityResponseType> {
    return this.http.post<IGame>(this.resourceUrl, game, { observe: 'response' });
  }

  update(game: IGame): Observable<EntityResponseType> {
    return this.http.put<IGame>(this.resourceUrl, game, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGame>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGame[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  joinPlayer(playerId: string): Observable<string> {
    return this.http.get<string>(`${this.joinUrl}${playerId}`);
  }

  findByPlayerId(playerId: number | undefined): Observable<EntityResponseType> {
    return this.http.get<IGame>(`${this.resourceUrl}/player/${playerId}`, {observe: 'response'});
  }

}
