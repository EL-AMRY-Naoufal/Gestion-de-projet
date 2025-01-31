import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDepartementDialogComponent } from './add-departement-dialog.component';

describe('AddDepartementDialogComponent', () => {
  let component: AddDepartementDialogComponent;
  let fixture: ComponentFixture<AddDepartementDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddDepartementDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddDepartementDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
