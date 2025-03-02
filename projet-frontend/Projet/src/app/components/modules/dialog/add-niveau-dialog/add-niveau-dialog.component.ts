import { Component, ViewEncapsulation } from '@angular/core';
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { CommonModule, NgForOf, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';

@Component({
  selector: 'app-add-niveau-dialog',
  standalone: true,
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
  templateUrl: './add-niveau-dialog.component.html',
  styleUrl: './add-niveau-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,

})
export class AddNiveauDialogComponent {

  newNiveau = { nom: '', semestres: [] };
  constructor(public dialogRef: MatDialogRef<AddNiveauDialogComponent>) {}

  onAdd(): void {
    this.dialogRef.close(this.newNiveau);
  }

  onCancel(): void {
    this.dialogRef.close();
  }


}
