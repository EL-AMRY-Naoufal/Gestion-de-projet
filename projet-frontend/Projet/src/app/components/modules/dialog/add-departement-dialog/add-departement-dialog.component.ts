import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-departement-dialog',
  standalone: true,
    imports: [
        FormsModule
    ],
  templateUrl: './add-departement-dialog.component.html',
  styleUrl: './add-departement-dialog.component.scss'
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
