import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddModulesDialogComponent } from './add-modules-dialog.component';

describe('AddModulesDialogComponent', () => {
  let component: AddModulesDialogComponent;
  let fixture: ComponentFixture<AddModulesDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddModulesDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddModulesDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
