import {Component, Inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import {NgForOf, NgIf} from '@angular/common';

import {EnseignantService} from "../../../../services/enseignant.service";
import {LoginService} from "../../../../services/login.service";
import {Affectation, Module} from "../../../shared/types/modules.types";


@Component({
  selector: 'app-create-affectation-dialog',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],

  templateUrl: './add-affectation.component.html',
  styleUrls: ['./add-affectation.component.scss']
})
export class AddAffectationComponent implements OnInit {
  myId!: string;
  enseignants!: any[];
  modules!: Module[];
  groupes!: any[];
  enseignantId!: number;
  groupeId!: number;
  heuresAssignees!: number;

  commentaire: string;
  dateAffectation: string;

  newAffectation!: Affectation;

  successMessage = '';
  errorMessage = '';

  constructor(
    private dialogRef: MatDialogRef<AddAffectationComponent>,
    private enseignantService: EnseignantService,
    private loginService: LoginService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.commentaire ="";
    this.dateAffectation = new Date().toLocaleDateString();
    console.log(this.dateAffectation);
  }

  ngOnInit(): void {
    this.myId = this.loginService.connectUser() + '';

    // Remplir les enseignants
    this.enseignantService.getEnseignants().subscribe(
      (response: any[]) => {
        this.enseignants = response;
         console.log('enseignants', this.enseignants);
      }
    );

    // Préremplir le module et le groupe
    if (this.data) {
      this.groupeId = this.data.groupeId;
    }
  }


  onSubmit(): void {
    if (this.enseignantId && this.groupeId && this.heuresAssignees) {

      //console select enseignant
      console.log('enseignantId', this.enseignants.find(enseignant => enseignant.id === Number(this.enseignantId)));
      this.enseignants.find(enseignant => enseignant.id === Number(this.enseignantId));
      this.newAffectation = {
        heuresAssignees: this.heuresAssignees,
        enseignantId: this.enseignantId,
        groupeId: this.groupeId,
        dateAffectation: this.dateAffectation,
        commentaire: this.commentaire
      };
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      this.successMessage = '';
    }
    //on close le dialog
    this.dialogRef.close(this.newAffectation);
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
