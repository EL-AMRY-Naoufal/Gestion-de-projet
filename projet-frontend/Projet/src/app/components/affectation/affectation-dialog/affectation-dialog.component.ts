import { Component, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AffectationListComponent } from "../affectation-enseignant/affectation-enseignant.component";
import { MatButtonModule } from "@angular/material/button";
import { MatDialogActions, MatDialogTitle } from "@angular/material/dialog";

@Component({
  selector: 'app-affectation-dialog',
  standalone: true,
  imports: [AffectationListComponent, MatDialogActions, MatDialogTitle, MatButtonModule],
  templateUrl: './affectation-dialog.component.html',
  styleUrls: ['./affectation-dialog.component.scss']
})
export class AffectationDialogComponent {

  enseignantId: string;

  constructor(
    private dialogRef: MatDialogRef<AffectationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.enseignantId = data.enseignantId; // Récupérer l'ID de l'enseignant
  }

  cancel() {
    this.dialogRef.close();
  }
}
