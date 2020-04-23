import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PokerTestModule } from '../../../test.module';
import { DealWinnerComponent } from 'app/entities/deal-winner/deal-winner.component';
import { DealWinnerService } from 'app/entities/deal-winner/deal-winner.service';
import { DealWinner } from 'app/shared/model/deal-winner.model';

describe('Component Tests', () => {
  describe('DealWinner Management Component', () => {
    let comp: DealWinnerComponent;
    let fixture: ComponentFixture<DealWinnerComponent>;
    let service: DealWinnerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokerTestModule],
        declarations: [DealWinnerComponent]
      })
        .overrideTemplate(DealWinnerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DealWinnerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DealWinnerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DealWinner(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dealWinners && comp.dealWinners[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
