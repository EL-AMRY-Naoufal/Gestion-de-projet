import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UpdateProfesseurComponent } from '../update-professeur/update-professeur.component';
import { EnseignantService } from '../../services/enseignant.service';
import { MenuComponent } from '../menu/menu.component';
import { UserCardComponent } from '../card/user-card/user-card.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-enseignants',
  standalone: true,
  providers: [EnseignantService],
  imports: [CommonModule, MenuComponent, UserCardComponent],
  templateUrl: './enseignants.component.html',
  styleUrls: ['./enseignants.component.scss']
})
export class EnseignantsComponent implements OnInit {
  users: any[] = [];

  constructor(
    private enseignantService: EnseignantService,
    private dialog: MatDialog
  ) {
    this.openDialog = this.openDialog.bind(this);
  }

  ngOnInit(): void {
    this.enseignantService.getEnseignants().subscribe(data => {
      this.users = data;
    });
  }

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
}
