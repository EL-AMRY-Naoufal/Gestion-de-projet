import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {Groupe} from "../../../shared/types/modules.types";

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

      // @ts-ignore
  newGroupe : Groupe = { nom: '', type: "", heuresAffectees: 0, totalHeuresDuGroupe: 0, moduleId: -1};

    constructor(public dialogRef: MatDialogRef<AddGroupeDialogComponent>) {}

    onAdd(): void {
      this.dialogRef.close(this.newGroupe);
    }

    onCancel(): void {
      this.dialogRef.close();
    }

}
