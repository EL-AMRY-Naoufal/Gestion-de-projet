import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import { Formation } from '../../../shared/types/modules.types';

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

  newFormation : Formation = { nom: '', responsableFormationId: -1, departementId: -1 };

  constructor(public dialogRef: MatDialogRef<AddFormationDialogComponent>) {}

  onAdd(): void {
    this.dialogRef.close(this.newFormation);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
