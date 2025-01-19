import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-add-annee-dialog',
  templateUrl: './add-annee-dialog.component.html',
  styleUrls: ['./add-annee-dialog.component.scss'],
  imports: [
    FormsModule
  ],
  standalone: true
})
export class AddAnneeDialogComponent {
  newAnnee = { debut: '', departements: [] };

  constructor(public dialogRef: MatDialogRef<AddAnneeDialogComponent>) {}

  onAdd(): void {
    this.dialogRef.close(this.newAnnee);
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
