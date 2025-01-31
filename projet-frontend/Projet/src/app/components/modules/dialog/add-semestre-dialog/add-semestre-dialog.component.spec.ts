import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSemestreDialogComponent } from './add-semestre-dialog.component';

describe('AddSemestreDialogComponent', () => {
  let component: AddSemestreDialogComponent;
  let fixture: ComponentFixture<AddSemestreDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddSemestreDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSemestreDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
