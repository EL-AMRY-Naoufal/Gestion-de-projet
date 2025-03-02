import { CommonModule, NgForOf, NgIf } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatOptionModule } from '@angular/material/core';
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-add-departement-dialog',
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
  templateUrl: './add-departement-dialog.component.html',
  styleUrl: './add-departement-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class AddDepartementDialogComponent {

  newDepartement = { nom: '', responsableDeDepartement: '', formations: [] };

  constructor(public dialogRef: MatDialogRef<AddDepartementDialogComponent>) {}

  onAdd(): void {
    this.dialogRef.close(this.newDepartement);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
