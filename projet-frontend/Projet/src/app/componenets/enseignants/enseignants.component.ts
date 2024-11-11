import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../menu/menu.component';
import { UserService } from '../../services/user.service';
import { UserCardComponent } from '../card/user-card/user-card.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { UpdateProfesseurComponent } from '../update-professeur/update-professeur.component';
import { RouterOutlet } from '@angular/router';
import { EnseignantService } from '../../services/enseignant.service';

@Component({
  selector: 'app-enseignants',
  standalone: true,
  imports: [CommonModule,MenuComponent,UserCardComponent, RouterOutlet, MatDialogModule, UpdateProfesseurComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './enseignants.component.html',
  styleUrl: './enseignants.component.scss'
})
export class EnseignantsComponent  implements OnInit{
 users: any[] = [];
  constructor(private userService: UserService, private dialog: MatDialog, 
    private enseignantService: EnseignantService) { }

  openDialog(enseignant?: any) {
    const dialogRef = this.dialog.open(UpdateProfesseurComponent, {
      data: enseignant,
      width: '500px',
      autoFocus: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Données reçues du modal:', result);
      }
    });
  }
  
  ngOnInit(): void {
    this.enseignantService.getEnseignants().subscribe(data => {
      this.users = data;
      //console.log("users", this.users);
    });  }

}
