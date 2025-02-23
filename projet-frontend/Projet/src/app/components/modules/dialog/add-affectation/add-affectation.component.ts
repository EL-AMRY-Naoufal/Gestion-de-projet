import {Component, Inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import {NgForOf, NgIf} from '@angular/common';
import {UserService} from "../../../../services/user.service";
import {ModuleService} from "../../../../services/module.service";
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
  // moduleId!: string;
  groupeId!: number;
  heuresAssignees!: number;

  commentaire: string;
  dateAffectation: string;

  newAffectation!: Affectation;

  successMessage = '';
  errorMessage = '';

  constructor(
    private dialogRef: MatDialogRef<AddAffectationComponent>,
    private affectationService: UserService,
    private moduleService: ModuleService,
    private enseignantService: EnseignantService,
    private loginService: LoginService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.commentaire ="";
    this.dateAffectation = new Date().toLocaleDateString();
    Date
    console.log(this.dateAffectation);
  }

  ngOnInit(): void {
    this.myId = this.loginService.connectUser() + '';

    // // Remplir les modules si nécessaire (si pas déjà préchargés)
    // this.moduleService.getModules().subscribe(
    //   (response: any[]) => {
    //     this.modules = response;
    //     this.onModuleChange();
    //   },
    //   (error: any) => {
    //     console.log('error', error);
    //   }
    // );

    // Remplir les enseignants
    this.enseignantService.getEnseignants().subscribe(
      (response: any[]) => {
        this.enseignants = response;
         console.log('enseignants', this.enseignants);
      }//,
      // (error: any) => {
      //   console.log('error', error);
      // }
    );

    // Préremplir le module et le groupe
    if (this.data) {
      // this.moduleId = this.data.moduleId;
      this.groupeId = this.data.groupeId;
    }
  }

  onModuleChange(): void {
    console.log("on module change");
    // const selectedModule = this.modules.find(module => module.id === Number(this.moduleId));
    // this.groupes = selectedModule ? selectedModule.groupes : [];
  }

  onSubmit(): void {
    if (this.enseignantId && this.groupeId && this.heuresAssignees) {

      //console select enseignant
      console.log('enseignantId', this.enseignants.find(enseignant => enseignant.id === Number(this.enseignantId)));

      const selectedEnseignant = this.enseignants.find(enseignant => enseignant.id === Number(this.enseignantId));
      const nomEnseignant = selectedEnseignant ? selectedEnseignant.firstname : 'nouveaux enseignant';

      this.newAffectation = {
        heuresAssignees: this.heuresAssignees,
        enseignantId: this.enseignantId,
        groupeId: this.groupeId,
        dateAffectation: this.dateAffectation,
        commentaire: this.commentaire
        // nomEnseignant: nomEnseignant,
      };
      this.affectationService
        .createAffectation(this.enseignantId.toString(), this.groupeId.toString(), this.heuresAssignees.toString())
        .subscribe({
          next: (response) => {
            alert('Affectation créée avec succès.');
            this.errorMessage = '';
            console.log('response : ', this.newAffectation);

            this.dialogRef.close(this.newAffectation);


          },
          error: (error: any) => {
            alert('Erreur lors de la création de l\'affectation :');
            this.successMessage = '';
            console.error('Erreur lors de la création de l\'affectation :', error);
          }
        });
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      this.successMessage = '';
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
