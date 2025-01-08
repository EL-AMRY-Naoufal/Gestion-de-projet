import { Component, OnInit } from '@angular/core';
import { EnseignantService } from '../../../services/enseignant.service';
import {NgForOf, NgIf} from "@angular/common";
import {AffectationType} from "../../shared/types/affectation.type";
import {LoginService} from "../../../services/login.service";

@Component({
  selector: 'app-affectation-list',
  standalone: true,
  templateUrl: 'affectation-enseignant.component.html',
  imports: [
    NgIf,
    NgForOf
  ],
  styleUrls: ['affectation-enseignant.component.scss']
})
export class AffectationListComponent implements OnInit {

  affectations: AffectationType[] = [];

  enseignantId!: string;

  constructor(private enseignantService: EnseignantService, private loginService: LoginService) { }

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
}
