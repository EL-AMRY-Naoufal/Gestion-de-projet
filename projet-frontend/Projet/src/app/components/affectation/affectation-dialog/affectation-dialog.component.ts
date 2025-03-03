import { Component, Inject, Input, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AffectationListComponent } from "../affectation-enseignant/affectation-enseignant.component";
import { MatDialogActions } from "@angular/material/dialog";
import { User } from '../../shared/types/user.type';
import { EnseignantDto } from '../../shared/types/enseignant.type';

@Component({
  selector: 'app-affectation-dialog',
  standalone: true,
  imports: [AffectationListComponent,MatDialogActions],
  templateUrl: './affectation-dialog.component.html',
  styleUrls: ['./affectation-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AffectationDialogComponent {

  enseignant: EnseignantDto;

  constructor(
    private dialogRef: MatDialogRef<AffectationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.enseignant = data.enseignant;
    console.log("wshsssss",this.enseignant);
  }

  cancel() {
    this.dialogRef.close();
  }
}
