import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';

import {CommonModule, NgForOf, NgIf} from '@angular/common';
import {catchError} from 'rxjs/operators';
import {UserService} from "../../../../services/user.service";
import {ModuleService} from "../../../../services/module.service";
import {MatFormField, MatFormFieldModule, MatLabel} from "@angular/material/form-field";
import {MatOption, MatSelect, MatSelectModule} from "@angular/material/select";
import {group} from "@angular/animations";
import {EnseignantService} from "../../../../services/enseignant.service";
import {LoginService} from "../../../../services/login.service";
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
    MatDialogModule,
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
    console.log("cc"+this.enseignantId+this.groupeId+this.heuresAssignees)
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
