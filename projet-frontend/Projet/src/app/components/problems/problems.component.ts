import {Component, OnInit} from '@angular/core';
import {ModuleService} from "../../services/module.service";
import {Groupe, Module} from "../shared/types/modules.types";
import {GroupeService} from "../../services/groupe.service";
import {NgForOf, NgIf} from "@angular/common";
import {EnseignantService} from "../../services/enseignant.service";
import {User} from "../shared/types/user.type";
import {EnseignantDto} from "../shared/types/enseignant.type";
import {AnneeService} from "../../services/annee.service";
import {YearService} from "../../services/year-service";
import { AffectationDialogComponent } from '../affectation/affectation-dialog/affectation-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-problems',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './problems.component.html',
  styleUrl: './problems.component.scss'
})
export class ProblemsComponent {

  groupeWithLowHours: Groupe[] = [];
  modules: Module[] = [];
  enseignantsWithLowHours: EnseignantDto[] = [];
  enseignantsWithHighHours: EnseignantDto[] = [];
  anneeSelectionnee: number = -1;
  selectedList: string = 'enseignants';


  constructor(
    public groupeService: GroupeService,
    public moduleService: ModuleService,
    public enseignantService: EnseignantService,
    public anneeService: YearService,
    private _dialog: MatDialog,
  ) {

    this.anneeSelectionnee = this.anneeService.getSelectedYearId() ?? -1
    this.loadGroupeWithsLowHours();
    this.loadEnseignantsWithLowHours();


  }


  loadGroupeWithsLowHours() {
    this.groupeService.getAllGroupes().subscribe((groupes: Groupe[]) => {
      this.moduleService.getAllModules().subscribe((modules: Module[]) => {
        this.groupeWithLowHours = groupes
          .filter(groupe => groupe.heuresAffectees < groupe.totalHeuresDuGroupe)
          .map(groupe => ({
            ...groupe,
            moduleNom: modules.find(module => module.id === groupe.moduleId)?.nom || 'Module inconnu'
          }));
      });
    });
  }

  loadEnseignantsWithLowHours() {
    if (this.anneeSelectionnee === -1) {
      console.warn("Aucune année sélectionnée. Impossible de charger les enseignants.");
      return;
    }

    this.enseignantService.getEnseignants().subscribe((enseignants: EnseignantDto[]) => {

      //const assign = enseignants[0].heuresAssignees[this.anneeSelectionnee];
      //console.log("Année sélectionnée :",assign);
     // console.log("Enseignants :", enseignants);
      this.enseignantsWithLowHours = enseignants.filter(enseignant =>
        this.getHeuresPourAnnee(enseignant, this.anneeSelectionnee) + enseignant.nbHeureCategorie < enseignant.maxHeuresService
      ).sort((b, a) =>
        (a.maxHeuresService - this.getHeuresPourAnnee(a, this.anneeSelectionnee) - a.nbHeureCategorie) -
        (b.maxHeuresService - this.getHeuresPourAnnee(b, this.anneeSelectionnee) - b.nbHeureCategorie)
      );

      this.enseignantsWithHighHours = enseignants
      .filter(enseignant =>
        this.getHeuresPourAnnee(enseignant, this.anneeSelectionnee) + enseignant.nbHeureCategorie > enseignant.maxHeuresService
      )
      .sort((a, b) =>
        (a.maxHeuresService - this.getHeuresPourAnnee(a, this.anneeSelectionnee) - a.nbHeureCategorie) -
        (b.maxHeuresService - this.getHeuresPourAnnee(b, this.anneeSelectionnee) - b.nbHeureCategorie)
      );


    });
  }

  getHeuresPourAnnee(enseignant: EnseignantDto, annee: number | null): number {
    if (!enseignant || !enseignant.heuresAssignees) {
      return 0;
    }
    const heures = (enseignant.heuresAssignees as unknown as Record<number, number>)[annee || 0];

    if (!heures) {
      return 0;
    }

    console.log("Heures pour ",heures + " pour l'enseignant " + enseignant.firstname + " " + enseignant.name);
    return heures;
  }

      showAffectations( enseignant: EnseignantDto) {
        console.log("affectation");

        // open modal
         this._dialog.open(AffectationDialogComponent, {
          disableClose: true,
          panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
          data: { enseignant: enseignant } // Passer l'enseignant en tant que donnée
        });
      }
}
