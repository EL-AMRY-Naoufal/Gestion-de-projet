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
import { YearService } from '../../services/year-service';
import { Year } from '../shared/types/year.type';

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
  selectedYear!: Year | null;

  constructor(
    private userService: UserService,
    private _usersService: UserService,
    private enseignantService: EnseignantService,
    private dialog: MatDialog,
    private _yearService: YearService
  ) {
    this.openDialog = this.openDialog.bind(this);
    this._view = 'card';
  }

  ngOnInit(): void {
    this.enseignantService.getEnseignants().subscribe((data) => {
      this.enseignants = data;
    });
    this._yearService.selectedYear$.subscribe((year) => {
      this.selectedYear = year;
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
    // Si la recherche est vide, recharger tous les enseignants
    if (this.searchQuery.trim() === '') {
      this.getEnseignants();
    } else {
      // Normalisation de la query
      const lowercaseQuery = this.searchQuery.toLowerCase();
      const uppercaseQuery = this.searchQuery.toUpperCase();
      const capitalizedQuery =
        this.searchQuery.charAt(0).toUpperCase() +
        this.searchQuery.slice(1).toLowerCase();

      // Tableau pour stocker les résultats combinés
      let allResults: EnseignantDto[] = [];

      // Création d'un tableau de requêtes
      const queries = [
        this.searchQuery,
        lowercaseQuery,
        uppercaseQuery,
        capitalizedQuery,
      ];

      queries.forEach((query) => {
        // Recherche par prénom (firstname)
        this.enseignantService
          .getEnseignantsByFirstname(query)
          .subscribe((data) => {
            allResults = this.mergeUniqueResults(allResults, data);
            this.enseignants = allResults;
          });

        // Recherche par nom (name)
        this.enseignantService.getEnseignantsByName(query).subscribe((data) => {
          allResults = this.mergeUniqueResults(allResults, data);
          this.enseignants = allResults;
        });
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

  getSelectedYear() {
    if (this.selectedYear != null) {
      return this.selectedYear.debut;
    }
    return 0;
  }
}
