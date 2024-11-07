import { Component, OnInit } from '@angular/core';
import { EnseignantService } from '../../services/enseignant.service';
import {NgForOf, NgIf} from "@angular/common";
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

  affectations: any[] = [];

  //normalement on doit récupérer l'id de l'enseignant connecté depuis le cookie par exemple ou par une requête HTTP, ou depuis un @input
  enseignantId: number = 3;

  constructor(private enseignantService: EnseignantService) { }

  ngOnInit(): void {
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
