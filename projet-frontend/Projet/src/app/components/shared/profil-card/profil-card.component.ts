import { User, UserRoleDto } from './../types/user.type';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';  // Pour utiliser les liens de navigation
import { MatCardModule } from '@angular/material/card';  // Pour le composant mat-card
import { MatIconModule } from '@angular/material/icon';  // Pour les icônes
import { MatButtonModule } from '@angular/material/button';  // Pour les boutons
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../services/login.service';
import { EnseignantDto, HeuresAssignees } from '../types/enseignant.type';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AffectationDialogComponent } from '../../affectation/affectation-dialog/affectation-dialog.component';
import { Year } from '../types/year.type';
import { concat } from 'rxjs';


@Component({
  selector: 'app-profil-card',
  standalone: true,
  imports: [
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    CommonModule,
  ],
  templateUrl: './profil-card.component.html',
  styleUrl: './profil-card.component.scss'
})
export class ProfilCardComponent {




    // private property to store user value
    private _enseignant: EnseignantDto;
    userRoles: string[] = [];
    @Input() openDialog!: (teacher?: EnseignantDto)=> void; // c'est quoi sa ????
    @Input() isYearSelected!: boolean;
    @Input() selectedYearId: number | null = null;



    /**
     * Component constructor
     */
    constructor(private loginService: LoginService,private _dialog: MatDialog,
    ) {
      this._enseignant = {} as EnseignantDto;
      this.userRoles = this.loginService.userRoles;

    }

    /**
     * Returns private property _person
     */
    get enseignant(): EnseignantDto {

      return this._enseignant;
    }

    /**
     * Sets private property _user
     */
    @Input()
    set enseignant(enseignant: EnseignantDto) {
      this._enseignant = enseignant;
    }



    /**
     * OnInit implementation
     */
    ngOnInit(): void {}



    showAffectations( enseignant: EnseignantDto) {
      console.log("affectation");

      // open modal
       this._dialog.open(AffectationDialogComponent, {
        disableClose: true,
        panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
        data: { enseignant: enseignant } // Passer l'enseignant en tant que donnée
      });
    }

    getCardClass(enseignant: EnseignantDto, selectedYearId: number | null): string {
      const heuresAssignees = this.getHeuresPourAnnee(enseignant, selectedYearId);
      const maxHeuresService = enseignant.maxHeuresService;
      const decharge = enseignant.nbHeureCategorie;

      if (heuresAssignees + decharge === 0) {
        return 'orange-card';
      } else if (heuresAssignees + decharge === maxHeuresService) {
        return 'green-card';
      } else {
        const ratio = (heuresAssignees+decharge) / maxHeuresService;
        if (ratio < 0.5) return 'light-orange-card';
        if (ratio < 0.8) return 'yellow-card';
        if (ratio == 1.0) return 'light-green-card';
        return 'blue-card';
      }
    }


    getHeuresPourAnnee(enseignant: EnseignantDto, annee: number | null): number {
      if (!enseignant || !enseignant.heuresAssignees) {
        return 0;
      }
      const heures = (enseignant.heuresAssignees as unknown as Record<number, number>)[annee || 0];

      if (!heures) {
        return 0;
      }
      return heures;
    }



}
