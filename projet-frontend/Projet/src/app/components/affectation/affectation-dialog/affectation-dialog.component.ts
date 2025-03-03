import { Component, Inject, Input, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AffectationListComponent } from "../affectation-enseignant/affectation-enseignant.component";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogActions, MatDialogTitle } from "@angular/material/dialog";
import { User } from '../../shared/types/user.type';

@Component({
  selector: 'app-affectation-dialog',
  standalone: true,
  imports: [AffectationListComponent, MatDialogActions, MatDialogTitle, MatButtonModule],
  templateUrl: './affectation-dialog.component.html',
  styleUrls: ['./affectation-dialog.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AffectationDialogComponent {

  user: User;

  constructor(
    private dialogRef: MatDialogRef<AffectationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.user = data.user;
  }

  cancel() {
    this.dialogRef.close();
  }
}
