import {Component, OnInit} from '@angular/core';
import {ModuleService} from "../../services/module.service";
import {Groupe, Module} from "../shared/types/modules.types";
import {GroupeService} from "../../services/groupe.service";
import {NgForOf} from "@angular/common";
import {EnseignantService} from "../../services/enseignant.service";
import {User} from "../shared/types/user.type";
import {EnseignantDto} from "../shared/types/enseignant.type";
import {AnneeService} from "../../services/annee.service";
import {YearService} from "../../services/year-service";

@Component({
  selector: 'app-problems',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './problems.component.html',
  styleUrl: './problems.component.scss'
})
export class ProblemsComponent {

  groupeWithLowHours: Groupe[] = [];
  modules: Module[] = [];
  enseignantsWithLowHours: EnseignantDto[] = [];
  anneeSelectionnee: number = -1;

  constructor(
    public groupeService: GroupeService,
    public moduleService: ModuleService,
    public enseignantService: EnseignantService,
    public anneeService: YearService
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
      console.log("Enseignants :", enseignants);
      this.enseignantsWithLowHours = enseignants.filter(enseignant =>
        this.getHeuresPourAnnee(enseignant, this.anneeSelectionnee) < enseignant.maxHeuresService
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
}
