import { Component, OnInit } from '@angular/core';
import { CommonModule, NgSwitch, NgSwitchCase } from '@angular/common';
import { UserService } from '../../services/user.service';
import { SearchBarComponent } from "../shared/search-bar/search-bar.component";
import { EnseignantService } from '../../services/enseignant.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { UpdateProfesseurComponent } from '../update-professeur/update-professeur.component';
import { MenuComponent } from '../shared/menu/menu.component';
import { ProfilCardComponent } from "../shared/profil-card/profil-card.component";
import { UserCardComponent } from '../shared/user-card/user-card.component';

@Component({
  selector: 'app-enseignants',
  standalone: true,
  providers: [EnseignantService],
  imports: [CommonModule, ProfilCardComponent],
  templateUrl: './enseignants.component.html',
  styleUrls: ['./enseignants.component.scss']
})

export class EnseignantsComponent  implements OnInit{
 users: any[] = [];
 private _view: string;
  constructor(private userService: UserService,
    private _usersService: UserService,
    private enseignantService: EnseignantService,
    private dialog: MatDialog) {
      this.openDialog = this.openDialog.bind(this);
      this._view = 'card';
    }

  ngOnInit(): void {
    this.enseignantService.getEnseignants().subscribe(data => {
      this.users = data;
    });  }

    openDialog(enseignant?: any) {
      const dialogRef = this.dialog.open(UpdateProfesseurComponent, {
        data: enseignant,
        width: '500px',
        autoFocus: true
      });

      dialogRef.afterClosed().subscribe(result => {
        this.enseignantService.getEnseignants().subscribe(data => {
          this.users = data;
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
}
