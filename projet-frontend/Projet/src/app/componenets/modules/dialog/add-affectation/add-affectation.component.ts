import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

import { NgForOf } from '@angular/common';
import { catchError } from 'rxjs/operators';
import {UserService} from "../../../../services/user.service";
import {ModuleService} from "../../../../services/module.service";
import {EnseignantService} from "../../../../services/enseignant.service";
import {LoginService} from "../../../../services/login.service";

@Component({
  selector: 'app-create-affectation-dialog',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],

  templateUrl: './add-affectation.component.html',
  styleUrls: ['./add-affectation.component.scss']
})
export class AddAffectationComponent implements OnInit {
  myId!: string;
  enseignants!: any[];
  modules!: any[];
  enseignantId!: string;
  moduleId!: string;
  heuresAssignees!: string;

  successMessage = '';
  errorMessage = '';

  constructor(
    private dialogRef: MatDialogRef<AddAffectationComponent>,
    private affectationService: UserService,
    private moduleService: ModuleService,
    private enseignantService: EnseignantService,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.myId = this.loginService.connectUser() + '';

    this.moduleService.getModules().subscribe(
      (response: any[]) => {
        this.modules = response;
        console.log('modules', this.modules);
      },
      (error: any) => {
        console.log('error', error);
      }
    );

    this.enseignantService.getEnseignants().subscribe(
      (response: any[]) => {
        this.enseignants = response;
        console.log('enseignants', this.enseignants);
      },
      (error: any) => {
        console.log('error', error);
      }
    );
  }

  onSubmit(): void {
    if (this.enseignantId && this.moduleId && this.heuresAssignees) {
      this.affectationService
        .createAffectation(this.enseignantId, this.moduleId, this.heuresAssignees)
        .subscribe({
          next: (response) => {
            this.successMessage = 'Affectation créée avec succès.';
            this.errorMessage = '';
            // Fermer le modal après succès
            this.dialogRef.close(true);
          },
          error: (error: any) => {
            this.errorMessage = "Une erreur s'est produite lors de la création de l'affectation.";
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
