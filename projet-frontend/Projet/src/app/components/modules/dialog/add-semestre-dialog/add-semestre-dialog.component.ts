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
  selector: 'app-add-semestre-dialog',
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
  templateUrl: './add-semestre-dialog.component.html',
  styleUrl: './add-semestre-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,

})
export class AddSemestreDialogComponent {

  newSemestre = { nom: '', modules: [] };

  constructor(public dialogRef: MatDialogRef<AddSemestreDialogComponent>) {}

  onAdd(): void {
    this.dialogRef.close(this.newSemestre);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
