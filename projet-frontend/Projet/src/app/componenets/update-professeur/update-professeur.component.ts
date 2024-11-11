import { CommonModule } from '@angular/common';
import { Component, Inject, ChangeDetectorRef  } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { UpdateProfesseurService } from '../../services/update-professeur.service';
import { CategorieEnseignantService } from '../../services/categorie-enseignant.service';
import { UserService } from '../../services/user.service';
import { User } from '../../types/user.types';
import { CategorieEnseignant, EnseignantDto } from '../../types/enseignant.type';

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
  providers: [UpdateProfesseurService],
  templateUrl: './update-professeur.component.html',
  styleUrl: './update-professeur.component.scss'
})
export class UpdateProfesseurComponent {
  defaultHeures = 192; 
  categories: string[] = [];
  enseignant!: EnseignantDto;

  utilisateurs: User[] = [];

  modules = [
    { id: 'module1', nom: 'Module 1' },
    { id: 'module2', nom: 'Module 2' },
    { id: 'module3', nom: 'Module 3' },
  ];

  isEdit = false; 

  constructor(
    private dialogRef: MatDialogRef<UpdateProfesseurComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private updateProfesseurService: UpdateProfesseurService,  
    private categorieService: CategorieEnseignantService,
    private userService: UserService,
  ) {
    if (data) {
      this.isEdit = true; 
      this.enseignant = { ...data }; 
    }
    this.enseignant = {
      id: this.data?.id || null,
      maxHeuresService: this.defaultHeures || 0,
      categorie: CategorieEnseignant.PROFESSEUR,
      heuresAssignees: 0
    };
  }

  ngOnInit(): void {
    this.categorieService.getCategories().subscribe(data => {
      this.categories = data;
    });
    this.updateProfesseurService.getEnseignantsNotInEnseignantTable().subscribe(data => {
      this.utilisateurs = data;
    });
  }
  

  save() {
    this.updateProfesseurService.createEnseignant(this.enseignant).subscribe(
      (response) => {
       this.dialogRef.close(this.enseignant); 
      },
      (error) => {
      }
    );
  }

  close() {
    this.dialogRef.close(); 
  }
}
