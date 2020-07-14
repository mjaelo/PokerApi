import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDealWinner, DealWinner } from 'app/shared/model/deal-winner.model';
import { DealWinnerService } from './deal-winner.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';
import { IGame } from 'app/shared/model/game.model';
import { GameService } from 'app/entities/game/game.service';

type SelectableEntity = IPlayer | IGame;

@Component({
  selector: 'jhi-deal-winner-update',
  templateUrl: './deal-winner-update.component.html'
})
export class DealWinnerUpdateComponent implements OnInit {
  isSaving = false;
  winners: IPlayer[] = [];
  games: IGame[] = [];

  editForm = this.fb.group({
    id: [],
    winner: [],
    game: []
  });

  constructor(
    protected dealWinnerService: DealWinnerService,
    protected playerService: PlayerService,
    protected gameService: GameService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealWinner }) => {
      this.updateForm(dealWinner);

      this.playerService
        .query({ filter: 'dealwinner-is-null' })
        .pipe(
          map((res: HttpResponse<IPlayer[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPlayer[]) => {
          if (!dealWinner.winner || !dealWinner.winner.id) {
            this.winners = resBody;
          } else {
            this.playerService
              .find(dealWinner.winner.id)
              .pipe(
                map((subRes: HttpResponse<IPlayer>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPlayer[]) => (this.winners = concatRes));
          }
        });

      this.gameService
        .query({ filter: 'dealwinner-is-null' })
        .pipe(
          map((res: HttpResponse<IGame[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IGame[]) => {
          if (!dealWinner.game || !dealWinner.game.id) {
            this.games = resBody;
          } else {
            this.gameService
              .find(dealWinner.game.id)
              .pipe(
                map((subRes: HttpResponse<IGame>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGame[]) => (this.games = concatRes));
          }
        });
    });
  }

  updateForm(dealWinner: IDealWinner): void {
    this.editForm.patchValue({
      id: dealWinner.id,
      winner: dealWinner.winner,
      game: dealWinner.game
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dealWinner = this.createFromForm();
    if (dealWinner.id !== undefined) {
      this.subscribeToSaveResponse(this.dealWinnerService.update(dealWinner));
    } else {
      this.subscribeToSaveResponse(this.dealWinnerService.create(dealWinner));
    }
  }

  private createFromForm(): IDealWinner {
    return {
      ...new DealWinner(),
      id: this.editForm.get(['id'])!.value,
      winner: this.editForm.get(['winner'])!.value,
      game: this.editForm.get(['game'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDealWinner>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
