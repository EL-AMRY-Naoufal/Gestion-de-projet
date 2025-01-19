import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOrientationDialogComponent } from './add-orientation-dialog.component';

describe('AddOrientationDialogComponent', () => {
  let component: AddOrientationDialogComponent;
  let fixture: ComponentFixture<AddOrientationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddOrientationDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddOrientationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
