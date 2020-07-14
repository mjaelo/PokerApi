import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IGame, Game } from 'app/shared/model/game.model';
import { GameService } from './game.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';

@Component({
  selector: 'jhi-game-update',
  templateUrl: './game-update.component.html'
})
export class GameUpdateComponent implements OnInit {
  isSaving = false;
  player2s: IPlayer[] = [];
  player1s: IPlayer[] = [];

  editForm = this.fb.group({
    id: [],
    card1: [],
    card2: [],
    card3: [],
    card4: [],
    card5: [],
    player1Id: [],
    player2Id: [],
    p1Pot: [],
    p2Pot: [],
    pot: [],
    player2: [],
    player1: []
  });

  constructor(
    protected gameService: GameService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ game }) => {
      this.updateForm(game);

      this.playerService
        .query({ filter: 'game-is-null' })
        .pipe(
          map((res: HttpResponse<IPlayer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPlayer[]) => {
          if (!game.player2 || !game.player2.id) {
            this.player2s = resBody;
          } else {
            this.playerService
              .find(game.player2.id)
              .pipe(
                map((subRes: HttpResponse<IPlayer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPlayer[]) => (this.player2s = concatRes));
          }
        });

      this.playerService
        .query({ filter: 'game-is-null' })
        .pipe(
          map((res: HttpResponse<IPlayer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPlayer[]) => {
          if (!game.player1 || !game.player1.id) {
            this.player1s = resBody;
          } else {
            this.playerService
              .find(game.player1.id)
              .pipe(
                map((subRes: HttpResponse<IPlayer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPlayer[]) => (this.player1s = concatRes));
          }
        });
    });
  }

  updateForm(game: IGame): void {
    this.editForm.patchValue({
      id: game.id,
      card1: game.card1,
      card2: game.card2,
      card3: game.card3,
      card4: game.card4,
      card5: game.card5,
      player1Id: game.player1Id,
      player2Id: game.player2Id,
      p1Pot: game.p1Pot,
      p2Pot: game.p2Pot,
      pot: game.pot,
      player2: game.player2,
      player1: game.player1
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const game = this.createFromForm();
    if (game.id !== undefined) {
      this.subscribeToSaveResponse(this.gameService.update(game));
    } else {
      this.subscribeToSaveResponse(this.gameService.create(game));
    }
  }

  private createFromForm(): IGame {
    return {
      ...new Game(),
      id: this.editForm.get(['id'])!.value,
      card1: this.editForm.get(['card1'])!.value,
      card2: this.editForm.get(['card2'])!.value,
      card3: this.editForm.get(['card3'])!.value,
      card4: this.editForm.get(['card4'])!.value,
      card5: this.editForm.get(['card5'])!.value,
      player1Id: this.editForm.get(['player1Id'])!.value,
      player2Id: this.editForm.get(['player2Id'])!.value,
      p1Pot: this.editForm.get(['p1Pot'])!.value,
      p2Pot: this.editForm.get(['p2Pot'])!.value,
      pot: this.editForm.get(['pot'])!.value,
      player2: this.editForm.get(['player2'])!.value,
      player1: this.editForm.get(['player1'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPlayer): any {
    return item.id;
  }
}
