import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { AffectationListComponent } from "../affectation-enseignant/affectation-enseignant.component";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from '../../../services/api-service';
import { EnseignantDto } from '../../shared/types/enseignant.type';

@Component({
  selector: 'app-affectation-dialog',
  standalone: true,
  imports: [AffectationListComponent,MatDialogActions],
  templateUrl: './affectation-dialog.component.html',
  styleUrl: './affectation-dialog.component.scss',
  encapsulation: ViewEncapsulation.None, // Désactive l'encapsulation pour appliquer les styles globaux

})
export class AffectationDialogComponent {

  enseignant: EnseignantDto;


  constructor(
    private dialogRef: MatDialogRef<AffectationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    this.enseignant = data.enseignant;
  }



  cancel() {
    this.dialogRef.close();
  }




}
