import { CommonModule } from '@angular/common';
import { Component, Inject, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { CategorieEnseignantService } from '../../services/categorie-enseignant.service';
import { UserService } from '../../services/user.service';
import { CategorieEnseignant, EnseignantDto } from '../shared/types/enseignant.type';
import { EnseignantService } from '../../services/enseignant.service';
import { User } from '../shared/types/user.type';

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
  providers: [EnseignantService],
  templateUrl: './update-professeur.component.html',
  styleUrl: './update-professeur.component.scss'
})
export class UpdateProfesseurComponent {
  defaultHeures = 192;
  categories: string[] = [];
  enseignant: EnseignantDto = {
    maxHeuresService: -1,
    categorieEnseignant: CategorieEnseignant.PROFESSEUR,
    heuresAssignees: 0,
    nbHeureCategorie: 0
  };
  categoriesEnseignant = Object.values(CategorieEnseignant);
  userName: string = '';
  utilisateurs: User[] = [];

  isEdit = false;

  constructor(
    private dialogRef: MatDialogRef<UpdateProfesseurComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private enseignantService: EnseignantService,
    private categorieService: CategorieEnseignantService,
  ) {
    if (data) {
      this.isEdit = true;
      this.userName = data.username;
      this.enseignantService.getEnseignant(data.id).subscribe({
        next: (enseignant) => {
          console.log(enseignant);
          this.enseignant = enseignant;
        },
        error: (error) => {
          console.error('Error fetching enseignant:', error);
          this.enseignant = {
            maxHeuresService: this.defaultHeures,
            categorieEnseignant: CategorieEnseignant.PROFESSEUR,
            heuresAssignees: 0,
            nbHeureCategorie: 0
          };
        }
      });
    }
    else {
      this.enseignant = {
        maxHeuresService: this.defaultHeures || 0,
        categorieEnseignant: CategorieEnseignant.PROFESSEUR,
        heuresAssignees: 0,
        nbHeureCategorie: 0
      };
    }
  }

  ngOnInit(): void {
    this.categorieService.getCategories().subscribe(data => {
      this.categories = data;
    });
    !this.isEdit && this.enseignantService.getEnseignantsNotInEnseignantTable().subscribe(data => {
      this.utilisateurs = data;
    });
  }


  save() {
    if (this.isEdit) {
      this.enseignantService.updateEnseignant(this.enseignant).subscribe(
        (response) => {
          this.dialogRef.close(this.enseignant);
        },
        (error) => {
        }
      );
    } else {
      this.enseignantService.createEnseignant(this.enseignant).subscribe(
        (response) => {
          this.dialogRef.close(this.enseignant);
        },
        (error) => {
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }
}
