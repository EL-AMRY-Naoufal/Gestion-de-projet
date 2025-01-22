import { Component, OnInit } from '@angular/core';
import { EnseignantService } from '../../../services/enseignant.service';
import {NgForOf, NgIf} from "@angular/common";
import {AffectationType} from "../../shared/types/affectation.type";
import {LoginService} from "../../../services/login.service";
import { MenuComponent } from "../../shared/menu/menu.component";
import { Router } from '@angular/router';

@Component({
  selector: 'app-affectation-list',
  standalone: true,
  templateUrl: 'affectation-enseignant.component.html',
  imports: [
    NgIf,
    NgForOf,
    MenuComponent
],
  styleUrls: ['affectation-enseignant.component.scss']
})
export class AffectationListComponent implements OnInit {

  affectations: AffectationType[] = [];

  enseignantId!: string;
  userRoles: string[] = [];

  constructor(private enseignantService: EnseignantService, private loginService: LoginService,private router: Router) {
    this.userRoles = this.loginService.userRoles;

  }



  ngOnInit(): void {

    this.enseignantId = this.loginService.connectUser() + '';

    this.enseignantService.getAffectationsByEnseignantId(this.enseignantId).subscribe(
      (data) => {
        this.affectations = data;
        console.log('Affectations:', this.affectations);  // Vérifier ici
      },
      (error) => {
        console.error('Erreur lors de la récupération des affectations', error);
      }
    );
  }


  getAffectations(): void {
    this.enseignantService.getAffectationsByEnseignantId(this.enseignantId).subscribe(
      data => {
        this.affectations = data;
      },
      error => {
        console.error('Erreur lors de la récupération des affectations :', error);
      }
    );
  }


    // Navigate to the component to create affectations by the admin
    navigateToCreateAffectations() {
      this.router.navigate(['/admin/affectations']);
    }
}
