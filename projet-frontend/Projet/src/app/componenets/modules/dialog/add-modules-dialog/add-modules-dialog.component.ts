import { Component } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { MatDialogRef } from "@angular/material/dialog";
import { Module } from "../../../../types/modules.types";
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
  newModule: Module = { nom: '', totalHeuresRequises: 0, groupes: [], heuresParType: new Map<string, number>() };
  types = ['CM', 'TD', 'TP']; // Types possibles
  heuresList: { type: string; heures: number }[] = []; // Liste des types et heures ajoutés

  constructor(public dialogRef: MatDialogRef<AddModulesDialogComponent>) {}

  // Ajouter un type à la liste
  addType(): void {
    this.heuresList.push({ type: '', heures: 0 });
  }

  // Supprimer un type de la liste
  removeType(index: number): void {
    this.heuresList.splice(index, 1);
  }

  // Sauvegarder le module avec les heures par type
  onAdd(): void {
    this.heuresList.forEach(entry => {
      this.newModule.heuresParType.set(entry.type, entry.heures);
    });
    this.dialogRef.close(this.newModule);
  }

  // Annuler l'opération
  onCancel(): void {
    this.dialogRef.close();
  }
}
