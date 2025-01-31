import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFormationDialogComponent } from './add-formation-dialog.component';

describe('AddFormationDialogComponent', () => {
  let component: AddFormationDialogComponent;
  let fixture: ComponentFixture<AddFormationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddFormationDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddFormationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
