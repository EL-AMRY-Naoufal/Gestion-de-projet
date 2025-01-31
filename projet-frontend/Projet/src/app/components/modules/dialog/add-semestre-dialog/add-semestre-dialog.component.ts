import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-add-semestre-dialog',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './add-semestre-dialog.component.html',
  styleUrl: './add-semestre-dialog.component.scss'
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
