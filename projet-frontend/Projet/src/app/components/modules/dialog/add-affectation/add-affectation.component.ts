import {Component, Inject, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

import {NgForOf, NgIf} from '@angular/common';
import {catchError} from 'rxjs/operators';
import {UserService} from "../../../../services/user.service";
import {ModuleService} from "../../../../services/module.service";
import {EnseignantService} from "../../../../services/enseignant.service";
import {LoginService} from "../../../../services/login.service";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect} from "@angular/material/select";
import {group} from "@angular/animations";
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
  enseignantId!: string;
  moduleId!: string;
  groupeId!: string;
  heuresAssignees!: string;

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
  }

  ngOnInit(): void {
    this.myId = this.loginService.connectUser() + '';

    // Remplir les modules si nécessaire (si pas déjà préchargés)
    this.moduleService.getModules().subscribe(
      (response: any[]) => {
        this.modules = response;
        this.onModuleChange();
      },
      (error: any) => {
        console.log('error', error);
      }
    );

    // Remplir les enseignants
    this.enseignantService.getEnseignants().subscribe(
      (response: any[]) => {
        this.enseignants = response;
        // console.log('enseignants', this.enseignants);
      },
      (error: any) => {
        console.log('error', error);
      }
    );

    // Préremplir le module et le groupe
    if (this.data) {
      this.moduleId = this.data.moduleId;
      this.groupeId = this.data.groupeId;
    }
  }

  onModuleChange(): void {
    const selectedModule = this.modules.find(module => module.id === Number(this.moduleId));
    this.groupes = selectedModule ? selectedModule.groupes : [];
  }

  onSubmit(): void {
    if (this.enseignantId && this.moduleId && this.heuresAssignees) {
      this.affectationService
        .createAffectation(this.enseignantId, this.moduleId, this.heuresAssignees)
        .subscribe({
          next: (response) => {
            alert('Affectation créée avec succès.');
            this.errorMessage = '';
            this.dialogRef.close(true);
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
