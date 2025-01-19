import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-groupe-dialog',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './add-groupe-dialog.component.html',
  styleUrl: './add-groupe-dialog.component.scss'
})
export class AddGroupeDialogComponent {

      newGroupe = { nom: '', heures: 0, type: '' };

    constructor(public dialogRef: MatDialogRef<AddGroupeDialogComponent>) {}

    onAdd(): void {
      this.dialogRef.close(this.newGroupe);
    }

    onCancel(): void {
      this.dialogRef.close();
    }

}
