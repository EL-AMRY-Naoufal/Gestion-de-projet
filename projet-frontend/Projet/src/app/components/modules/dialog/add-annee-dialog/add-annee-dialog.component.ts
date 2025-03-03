import { Component, ViewEncapsulation } from '@angular/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Annee } from '../../../shared/types/modules.types';
import { CommonModule, NgForOf, NgIf } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatOptionModule } from '@angular/material/core';

@Component({
  selector: 'app-add-annee-dialog',
  templateUrl: './add-annee-dialog.component.html',
  styleUrls: ['./add-annee-dialog.component.scss'],
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    CommonModule,
    FormsModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    MatDialogModule,
  ],
  standalone: true,
  encapsulation: ViewEncapsulation.None,
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
