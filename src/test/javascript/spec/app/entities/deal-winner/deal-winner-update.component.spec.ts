import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PokerTestModule } from '../../../test.module';
import { DealWinnerUpdateComponent } from 'app/entities/deal-winner/deal-winner-update.component';
import { DealWinnerService } from 'app/entities/deal-winner/deal-winner.service';
import { DealWinner } from 'app/shared/model/deal-winner.model';

describe('Component Tests', () => {
  describe('DealWinner Management Update Component', () => {
    let comp: DealWinnerUpdateComponent;
    let fixture: ComponentFixture<DealWinnerUpdateComponent>;
    let service: DealWinnerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PokerTestModule],
        declarations: [DealWinnerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DealWinnerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DealWinnerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DealWinnerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DealWinner(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DealWinner();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
