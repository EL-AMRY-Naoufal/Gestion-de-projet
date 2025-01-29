import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";
import {Groupe, Module, TypeHeure} from "../../../../types/modules.types";
import { NgForOf } from "@angular/common";

@Component({
  selector: 'app-add-modules-dialog',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './add-modules-dialog.component.html',
  styleUrl: './add-modules-dialog.component.scss'
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
          nom: `${entry.type} Groupe ${i + 1}`, // Exemple de nom de groupe
          heures: entry.heures,
          type: entry.type,
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
