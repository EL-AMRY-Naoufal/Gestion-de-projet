import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { AffectationListComponent } from "../affectation-enseignant/affectation-enseignant.component";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from '../../../services/api-service';

@Component({
  selector: 'app-affectation-dialog',
  standalone: true,
  imports: [AffectationListComponent,MatDialogActions],
  templateUrl: './affectation-dialog.component.html',
  styleUrl: './affectation-dialog.component.scss',
  encapsulation: ViewEncapsulation.None, // Désactive l'encapsulation pour appliquer les styles globaux

})
export class AffectationDialogComponent {

 // editedComment: string;

  constructor(
    private dialogRef: MatDialogRef<AffectationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
  }



  cancel() {
    this.dialogRef.close();
  }


  onSave($event: SubmitEvent) {
    throw new Error('Method not implemented.');
    }


}
