import {Component, OnInit} from '@angular/core';
import {UserService} from '../../../services/user.service';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ModuleService} from "../../../services/module.service";
import {EnseignantService} from "../../../services/enseignant.service";
import {LoginService} from "../../../services/login.service";
import {catchError} from "rxjs/operators";
import { MenuComponent } from "../../shared/menu/menu.component";

@Component({
  selector: 'app-create-affectation',
  templateUrl: './create-affectation.component.html',
  styleUrls: ['./create-affectation.component.scss'],
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    MenuComponent
],
  standalone: true
})
export class CreateAffectationComponent implements OnInit {

  myId!: string
  enseignants!: any[]
  modules!: any[]
  enseignantId!: string
  moduleId!: string
  heuresAssignees!: string

  successMessage = '';
  errorMessage = '';

  constructor(private affectationService: UserService,
              private moduleService: ModuleService,
              private enseinganntServices: EnseignantService,
              private loginService: LoginService
  ) {
  }

  ngOnInit(): void {

    this.myId = this.loginService.connectUser() + '';

    // this.moduleService.getModules().subscribe(
    //   (response) => {
    //     this.modules = response;
    //     console.log("modules", this.modules);
    //   },
    //   catchError => {
    //     console.log("error", catchError);
    //   }
    // );


    this.enseinganntServices.getEnseignants().subscribe(
      (response) => {
        this.enseignants = response;
        console.log("enseignants", this.enseignants);
      },
      catchError => {
        console.log("error", catchError);
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
          },
          error: (error) => {
            this.errorMessage = "Une erreur s'est produite lors de la création de l'affectation.";
            this.successMessage = '';
            console.error('Erreur lors de la création de l\'affectation :', error);
          },
        });
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      this.successMessage = '';
    }
  }
}
