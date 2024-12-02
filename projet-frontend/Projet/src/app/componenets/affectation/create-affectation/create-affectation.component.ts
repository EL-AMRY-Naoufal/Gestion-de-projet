import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../services/user.service';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-create-affectation',
  templateUrl: './create-affectation.component.html',
  styleUrls: ['./create-affectation.component.scss'],
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  standalone: true
})
export class CreateAffectationComponent implements OnInit {
  enseignants = [
    { id: 1, name: 'Enseignant 1' },
    { id: 2, name: 'Enseignant 2' },
  ]; // Remplacez par une requête API si nécessaire

  modules = [
    { id: 1, name: 'Module 1' },
    { id: 2, name: 'Module 2' },
  ]; // Remplacez par une requête API si nécessaire

  enseignantId: string | null = null;
  moduleId: string | null = null;
  heuresAssignees: string | null = null;
  successMessage = '';
  errorMessage = '';

  constructor(private affectationService: UserService) {}

  ngOnInit(): void {}

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
          },
        });
    } else {
      this.errorMessage = 'Veuillez remplir tous les champs.';
      this.successMessage = '';
    }
  }
}
