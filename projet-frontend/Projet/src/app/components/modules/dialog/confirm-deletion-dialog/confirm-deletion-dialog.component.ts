import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AddModulesDialogComponent } from '../add-modules-dialog/add-modules-dialog.component';

@Component({
  selector: 'app-confirm-deletion-dialog',
  standalone: true,
  imports: [],
  templateUrl: './confirm-deletion-dialog.component.html',
  styleUrl: './confirm-deletion-dialog.component.scss'
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
