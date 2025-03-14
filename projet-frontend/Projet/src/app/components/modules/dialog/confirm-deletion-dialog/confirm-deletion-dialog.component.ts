import { AddModulesDialogComponent } from '../add-modules-dialog/add-modules-dialog.component';
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
  selector: 'app-confirm-deletion-dialog',
  standalone: true,
  imports: [FormsModule,
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
  templateUrl: './confirm-deletion-dialog.component.html',
  styleUrl: './confirm-deletion-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,

})
export class ConfirmDeletionDialogComponent {
  constructor(public dialogRef: MatDialogRef<AddModulesDialogComponent>) {}

  onCancel() : void {
    this.dialogRef.close(false);
  }

  onSubmit() : void {
    this.dialogRef.close(true);
  }
}
