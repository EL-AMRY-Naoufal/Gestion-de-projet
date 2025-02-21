import { Component, OnInit } from '@angular/core';
import { CommonModule, NgSwitch, NgSwitchCase } from '@angular/common';
import { UserService } from '../../services/user.service';
import { SearchBarComponent } from '../shared/search-bar/search-bar.component';
import { EnseignantService } from '../../services/enseignant.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { UpdateProfesseurComponent } from '../update-professeur/update-professeur.component';
import { MenuComponent } from '../shared/menu/menu.component';
import { ProfilCardComponent } from '../shared/profil-card/profil-card.component';
import { UserCardComponent } from '../shared/user-card/user-card.component';
import { EnseignantDto } from '../shared/types/enseignant.type';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-enseignants',
  standalone: true,
  providers: [EnseignantService],
  imports: [CommonModule, ProfilCardComponent, FormsModule],
  templateUrl: './enseignants.component.html',
  styleUrls: ['./enseignants.component.scss'],
})
export class EnseignantsComponent implements OnInit {
  enseignants: EnseignantDto[] = [];
  private _view: string;
  searchQuery: string = '';

  constructor(
    private userService: UserService,
    private _usersService: UserService,
    private enseignantService: EnseignantService,
    private dialog: MatDialog
  ) {
    this.openDialog = this.openDialog.bind(this);
    this._view = 'card';
  }

  ngOnInit(): void {
    this.enseignantService.getEnseignants().subscribe((data) => {
      this.enseignants = data;
    });
  }

    openDialog(enseignant?: EnseignantDto): void {
      const dialogRef = this.dialog.open(UpdateProfesseurComponent, {
        data: enseignant,
        panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée

        //autoFocus: true
      });

    dialogRef.afterClosed().subscribe((result) => {
      this.enseignantService.getEnseignants().subscribe((data) => {
        this.enseignants = data;
      });
      if (result) {
        console.log('Données reçues du modal:', result);
      }
    });
  }

  /**
   * Returns private property _view
   */
  get view(): string {
    return this._view;
  }

  getEnseignants(): void {
    this.enseignantService.getEnseignants().subscribe((data) => {
      this.enseignants = data;
    });
  }

  searchEnseignants(): void {
    console.log('query', this.searchQuery);

    // Si la recherche est vide, recharger tous les enseignants
    if (this.searchQuery.trim() === '') {
      this.getEnseignants();
    } else {
      // Tableau pour stocker les résultats combinés
      let allResults: EnseignantDto[] = [];

      // Recherche par prénom (firstname)
      this.enseignantService
        .getEnseignantsByFirstname(this.searchQuery) // Recherche par firstname
        .subscribe((data) => {
          console.log('by firstname', data);
          allResults = this.mergeUniqueResults(allResults, data);

          // Affiche les résultats combinés après la recherche par prénom
          this.enseignants = allResults;
        });

      // Recherche par nom (name)
      this.enseignantService
        .getEnseignantsByName(this.searchQuery) // Recherche par name
        .subscribe((data) => {
          console.log('by name', data);
          allResults = this.mergeUniqueResults(allResults, data);

          // Affiche les résultats combinés après la recherche par nom
          this.enseignants = allResults;
        });
    }
  }

  // Méthode pour fusionner les résultats et supprimer les doublons par ID
  mergeUniqueResults(
    existingResults: EnseignantDto[],
    newResults: EnseignantDto[]
  ): EnseignantDto[] {
    const uniqueResults = [...existingResults, ...newResults];
    const uniqueIds = new Set();

    return uniqueResults.filter((enseignant) => {
      if (!uniqueIds.has(enseignant.id)) {
        uniqueIds.add(enseignant.id);
        return true;
      }
      return false;
    });
  }
}
