import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from "@angular/forms";
import { Annee } from '../../../shared/types/modules.types';

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
  newAnnee: Annee = { debut: new Date().getFullYear() };

  constructor(public dialogRef: MatDialogRef<AddAnneeDialogComponent>) {}

  isAnneeValide(): boolean {
    //verifier si l'année est un nombre
    if (isNaN(this.newAnnee.debut)) {
      return false;
    }
    return true;
  }

  onAdd(): void {
    if (this.isAnneeValide()) {
      this.dialogRef.close(this.newAnnee);
    } else {
      alert('L\'année n\'est pas valide.');
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
