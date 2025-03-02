import { Component, ViewEncapsulation } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { CommonModule, NgForOf, NgIf } from "@angular/common";
import { TypeHeure, Module, Groupe } from '../../../shared/types/modules.types';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-add-modules-dialog',
  standalone: true,
  imports: [
  FormsModule,
     NgForOf,
     NgIf,
     CommonModule,
     FormsModule,
     MatIconModule,
     MatFormFieldModule,
     MatInputModule,
     MatSelectModule,
     MatOptionModule,
     MatCheckboxModule,
     ReactiveFormsModule,
     MatDialogModule,
  ],
  templateUrl: './add-modules-dialog.component.html',
  styleUrl: './add-modules-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,

})
export class AddModulesDialogComponent {

  newModule: Module = { nom: '', groupes: [], heuresParType: new Map<string, number>() , nombreGroupes: 0 };
  types = Object.values(TypeHeure);

  heuresList = this.types.map(type => ({ type, heures: 0, groupes: 0 }));

  constructor(public dialogRef: MatDialogRef<AddModulesDialogComponent>) {}

  onAdd(): void {
    this.heuresList.forEach(entry => {
      // Sauvegarde des heures pour chaque type
      this.newModule.heuresParType.set(entry.type, entry.heures);

      // Créer les groupes en fonction du nombre de groupes spécifié pour chaque type
      for (let i = 0; i < entry.groupes; i++) {
        const newGroupe: Groupe = {
          nom: `${entry.type} Groupe ${i + 1}`,
          type: entry.type,
          heuresAffectees: 0,
          totalHeuresDuGroupe: entry.heures,
          affectations: []
        };
        console.log(newGroupe);
        this.newModule.groupes.push(newGroupe);
      }
    });

    // Fermer la fenêtre de dialogue avec le nouveau module, incluant les groupes
    this.dialogRef.close(this.newModule);
  }


  // Annuler l'opération
  onCancel(): void {
    this.dialogRef.close();
  }
}
