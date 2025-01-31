import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGroupeDialogComponent } from './add-groupe-dialog.component';

describe('AddGroupeDialogComponent', () => {
  let component: AddGroupeDialogComponent;
  let fixture: ComponentFixture<AddGroupeDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddGroupeDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddGroupeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
