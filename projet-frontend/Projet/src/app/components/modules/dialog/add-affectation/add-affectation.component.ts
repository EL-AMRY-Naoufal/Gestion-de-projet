import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';

import {CommonModule, NgForOf, NgIf} from '@angular/common';
import {catchError} from 'rxjs/operators';
import {UserService} from "../../../../services/user.service";
import {ModuleService} from "../../../../services/module.service";
import {EnseignantService} from "../../../../services/enseignant.service";
import {LoginService} from "../../../../services/login.service";
import {MatFormField, MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect, MatSelectModule} from "@angular/material/select";
import {group} from "@angular/animations";
import {Affectation, Module} from "../../../shared/types/modules.types";
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';


@Component({
  selector: 'app-create-affectation-dialog',
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
    MatDialogModule, // ✅ Ajout du module MatDialog
  ],

  templateUrl: './add-affectation.component.html',
  styleUrls: ['./add-affectation.component.scss'],
  encapsulation: ViewEncapsulation.None, // Désactive l'encapsulation pour appliquer les styles globaux

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
         console.log('enseignants', this.enseignants);
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
    console.log("cc"+this.enseignantId+this.groupeId+this.heuresAssignees)
    if (this.enseignantId && this.groupeId && this.heuresAssignees) {

      //console select enseignant
      console.log('enseignantId', this.enseignants.find(enseignant => enseignant.id === Number(this.enseignantId)));

      const selectedEnseignant = this.enseignants.find(enseignant => enseignant.id === Number(this.enseignantId));
      const nomEnseignant = selectedEnseignant ? selectedEnseignant.firstname : 'nouveaux enseignant';

      this.newAffectation = {
        nomEnseignant: nomEnseignant,
        heuresAssignees: Number(this.heuresAssignees)
      };
      this.affectationService
        .createAffectation(this.enseignantId, this.groupeId, this.heuresAssignees)
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
