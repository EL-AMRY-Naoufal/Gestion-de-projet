import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-orientation-dialog',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './add-orientation-dialog.component.html',
  styleUrl: './add-orientation-dialog.component.scss'
})
export class AddOrientationDialogComponent {

    newOrientation = { nom: '', responsableOrientation: '', totalHeures: 0, semestres: [] };

    constructor(public dialogRef: MatDialogRef<AddOrientationDialogComponent>) {}

    onAdd(): void {
      this.dialogRef.close(this.newOrientation);
    }

    onCancel(): void {
      this.dialogRef.close();
    }

}
