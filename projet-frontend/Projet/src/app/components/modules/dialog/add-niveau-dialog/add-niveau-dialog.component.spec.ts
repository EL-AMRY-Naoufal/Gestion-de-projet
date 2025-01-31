import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNiveauDialogComponent } from './add-niveau-dialog.component';

describe('AddNiveauDialogComponent', () => {
  let component: AddNiveauDialogComponent;
  let fixture: ComponentFixture<AddNiveauDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddNiveauDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNiveauDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
