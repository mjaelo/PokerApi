import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDealWinner, DealWinner } from 'app/shared/model/deal-winner.model';
import { DealWinnerService } from './deal-winner.service';
import { DealWinnerComponent } from './deal-winner.component';
import { DealWinnerDetailComponent } from './deal-winner-detail.component';
import { DealWinnerUpdateComponent } from './deal-winner-update.component';

@Injectable({ providedIn: 'root' })
export class DealWinnerResolve implements Resolve<IDealWinner> {
  constructor(private service: DealWinnerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDealWinner> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dealWinner: HttpResponse<DealWinner>) => {
          if (dealWinner.body) {
            return of(dealWinner.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DealWinner());
  }
}

export const dealWinnerRoute: Routes = [
  {
    path: '',
    component: DealWinnerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pokerApp.dealWinner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DealWinnerDetailComponent,
    resolve: {
      dealWinner: DealWinnerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pokerApp.dealWinner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DealWinnerUpdateComponent,
    resolve: {
      dealWinner: DealWinnerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pokerApp.dealWinner.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DealWinnerUpdateComponent,
    resolve: {
      dealWinner: DealWinnerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pokerApp.dealWinner.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
