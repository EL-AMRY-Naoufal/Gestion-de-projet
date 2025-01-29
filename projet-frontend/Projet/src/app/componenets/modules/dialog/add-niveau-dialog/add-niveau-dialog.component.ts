import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import {Semestre} from "../../../../types/modules.types";

@Component({
  selector: 'app-add-niveau-dialog',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './add-niveau-dialog.component.html',
  styleUrl: './add-niveau-dialog.component.scss'
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
