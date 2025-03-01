import { User, UserRoleDto } from './../types/user.type';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';  // Pour utiliser les liens de navigation
import { MatCardModule } from '@angular/material/card';  // Pour le composant mat-card
import { MatIconModule } from '@angular/material/icon';  // Pour les icônes
import { MatButtonModule } from '@angular/material/button';  // Pour les boutons
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../services/login.service';
import { EnseignantDto } from '../types/enseignant.type';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AffectationDialogComponent } from '../../affectation/affectation-dialog/affectation-dialog.component';


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
    // private property to store delete$ value
    private readonly _delete$: EventEmitter<EnseignantDto>;
    userRoles: string[] = [];
    @Input() openDialog!: (teacher?: EnseignantDto)=> void;


    /**
     * Component constructor
     */
    constructor(private loginService: LoginService,private _dialog: MatDialog,
    ) {
      this._enseignant = {} as EnseignantDto;
      this._delete$ = new EventEmitter<EnseignantDto>();
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
     * Returns private property _delete$
     */
    @Output('deleteProfil') get delete$(): EventEmitter<EnseignantDto> {
      return this._delete$;
    }

    /**
     * OnInit implementation
     */
    ngOnInit(): void {}

    /**
     * Function to emit event to delete current user
     */
    delete(enseignant: EnseignantDto): void {
      this._delete$.emit(enseignant);
    }

    showAffectations( enseignant: EnseignantDto) {
      console.log("affectation");

      // open modal
       this._dialog.open(AffectationDialogComponent, {
        disableClose: true,
        panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
        data: { enseignant: enseignant } // Passer l'enseignant en tant que donnée
      });
    }


}
