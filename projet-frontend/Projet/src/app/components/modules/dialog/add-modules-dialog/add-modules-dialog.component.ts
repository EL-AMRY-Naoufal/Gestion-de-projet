import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";
import { NgForOf } from "@angular/common";
import { TypeHeure, Module, Groupe } from '../../../shared/types/modules.types';

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

  //Utilisation d'un objet non typé à cause de la création des groupes (absents du type "Module")
  newModule: any = { nom: '', heuresParType: new Map<string, number>(), };
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
