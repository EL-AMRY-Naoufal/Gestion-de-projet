import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAffectationComponent } from './add-affectation.component';

describe('AddAffectationComponent', () => {
  let component: AddAffectationComponent;
  let fixture: ComponentFixture<AddAffectationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddAffectationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddAffectationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
