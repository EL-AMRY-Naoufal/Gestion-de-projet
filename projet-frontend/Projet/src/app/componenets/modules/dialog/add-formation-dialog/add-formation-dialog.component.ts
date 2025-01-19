import { Component } from '@angular/core';
import {Module, Niveau} from "../../../../types/modules.types";
import {MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-add-formation-dialog',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './add-formation-dialog.component.html',
  styleUrl: './add-formation-dialog.component.scss'
})
export class AddFormationDialogComponent {

  newFormation = { nom: '', responsableFormation: '', totalHeures: 0, niveaux: [] };

  constructor(public dialogRef: MatDialogRef<AddFormationDialogComponent>) {}

  onAdd(): void {
    this.dialogRef.close(this.newFormation);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
