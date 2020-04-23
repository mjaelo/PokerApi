import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PokerTestModule } from '../../../test.module';
import { DealWinnerDetailComponent } from 'app/entities/deal-winner/deal-winner-detail.component';
import { DealWinner } from 'app/shared/model/deal-winner.model';

describe('Component Tests', () => {
  describe('DealWinner Management Detail Component', () => {
    let comp: DealWinnerDetailComponent;
    let fixture: ComponentFixture<DealWinnerDetailComponent>;
    const route = ({ data: of({ dealWinner: new DealWinner(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokerTestModule],
        declarations: [DealWinnerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DealWinnerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DealWinnerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dealWinner on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dealWinner).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
