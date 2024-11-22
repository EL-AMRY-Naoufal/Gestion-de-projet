import { CommonModule } from '@angular/common';
import { Component, Inject, ChangeDetectorRef  } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';

@Component({
  selector: 'app-update-professeur',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
  ],
  templateUrl: './update-professeur.component.html',
  styleUrl: './update-professeur.component.scss'
})
export class UpdateProfesseurComponent {
  defaultHeures = 192; 
  enseignant = {
    nombreHeures: this.defaultHeures || 0,
    profession: '',
    utilisateur: '',
    modules: []
  };

  // Liste d'utilisateurs et de modules
  utilisateurs = [
    { id: 1, nom: 'Utilisateur 1' },
    { id: 2, nom: 'Utilisateur 2' },
    { id: 3, nom: 'Utilisateur 3' },
  ];

  modules = [
    { id: 'module1', nom: 'Module 1' },
    { id: 'module2', nom: 'Module 2' },
    { id: 'module3', nom: 'Module 3' },
  ];

  isEdit = false; 

  constructor(
    private dialogRef: MatDialogRef<UpdateProfesseurComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
    if (data) {
      this.isEdit = true; 
      this.enseignant = { ...data }; 
    }
  }
  

  save() {
    console.log('Enseignant sauvegardé:', this.enseignant);
    this.dialogRef.close(this.enseignant); 
  }

  close() {
    this.dialogRef.close(); 
  }
}
