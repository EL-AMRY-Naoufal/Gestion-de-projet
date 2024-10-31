import { Component } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { UpdateProfesseurComponent } from '../update-professeur/update-professeur.component';
import { MatIconModule } from '@angular/material/icon';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DashboardComponent, RouterOutlet, MatIconModule, MatDialogModule, UpdateProfesseurComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  constructor(private dialog: MatDialog) {}

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
}
