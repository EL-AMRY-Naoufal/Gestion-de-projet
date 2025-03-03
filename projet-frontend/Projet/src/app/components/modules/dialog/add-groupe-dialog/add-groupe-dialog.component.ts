import { NgForOf, NgIf, CommonModule } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatOptionModule } from '@angular/material/core';
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-add-groupe-dialog',
  standalone: true,
  imports: [
    FormsModule,
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
  templateUrl: './add-groupe-dialog.component.html',
  styleUrl: './add-groupe-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,

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
