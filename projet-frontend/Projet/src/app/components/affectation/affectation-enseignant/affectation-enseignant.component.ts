import { Component, input, Input, OnInit, Output } from '@angular/core';
import { EnseignantService } from '../../../services/enseignant.service';
import { NgForOf, NgIf } from '@angular/common';
import { LoginService } from '../../../services/login.service';
import { User } from '../../shared/types/user.type';
import { ActivatedRoute } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api-service';

import { Affectation, CoAffectation } from '../../shared/types/modules.types';
import { EnseignantDto } from '../../shared/types/enseignant.type';
import { GroupeService } from '../../../services/groupe.service';
import { ModuleService } from '../../../services/module.service';
import { AffectationService } from '../../../services/affectation.service';
import { Year } from '../../shared/types/year.type';
import { YearService } from '../../../services/year-service';
import { AnneeService } from '../../../services/annee.service';

@Component({
  selector: 'app-affectation-list',
  standalone: true,
  templateUrl: 'affectation-enseignant.component.html',
  imports: [
    NgIf,
    NgForOf,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
  ],
  styleUrls: ['affectation-enseignant.component.scss'],
})
export class AffectationListComponent implements OnInit {
  affectations: Affectation[] = [];
  nomGroupes: { [key: number]: string } = {};
  nomModules: { [key: number]: string } = {};
  coAffectations: { [key: number]: CoAffectation[] } = {};
  selectedModuleId: number | null = null;
  selectedYear: Year | null = null;

  @Input() dialog: boolean = false; // Permet de savoir si le composant est utilisé dans un dialogue
  @Input() enseignantId!: string;

  username: string = '';

  editingId: number | null = null;
  editedComment: string = '';
  connect: boolean = false; // Si on utilise l'utilisateur connecter

  constructor(
    private enseignantService: EnseignantService,
    private loginService: LoginService,
    private affectationsService: AffectationService,
    private authService: ApiService,
    private activatedRoute: ActivatedRoute,
    private groupeService: GroupeService,
    private moduleService: ModuleService,
    private _api: ApiService,
    private yearService: YearService,
    private anneeService: AnneeService
  ) {}

  ngOnInit(): void {
    this.yearService.selectedYear$.subscribe((year) => {
      this.selectedYear = year;
      console.log("selected year", this.selectedYear);
      if (this.enseignantId) {
        this.loadAffectations();
      }
    });

    if (this.dialog == false) {
      if (this.enseignantId) {
        this.loadData();
      } else {
        this.fetchEnseignantIdFromUser();
      }
    }

  }

  private loadData(): void {
    this.loadAffectations();
    this.loadEnseignantName(Number(this.enseignantId));
  }

  private setEnseignantIdAndLoadData(enseignantId: string): void {
    this.enseignantId = enseignantId;
    this.loadAffectations();
    this.loadEnseignantName(Number(enseignantId));
  }

  private fetchEnseignantIdFromUser(): void {
    this.enseignantService
      .getEnseignantByUserId(this.loginService.connectUser())
      .subscribe(
        (enseignant: EnseignantDto) => {
          if (enseignant.id) {
            this.setEnseignantIdAndLoadData(enseignant.id.toString());
          }
        },
        (error) => {
          console.error(
            'Erreur lors de la récupération de l enseignant',
            error
          );
        }
      );

    this.connect = true;
  }

  /* private loadAffectations(): void {
    this.enseignantService.getAffectationsByEnseignantId(this.enseignantId)
      .subscribe(
        (data) => {
          this.affectations = data;
          console.log('Affectations:', this.affectations);

          // Charger les noms des modules et groupes après avoir reçu les affectations
          this.affectations.forEach(affectation => {
            this.loadModuleName(affectation.moduleId||-1);
            this.loadGroupName(affectation.groupeId);
          });
        },
        (error) => {
          console.error('Erreur lors de la récupération des affectations', error);
        }
      );
  }*/
  private loadAffectations(): void {
    this.enseignantService.getAffectationsByEnseignantId(this.enseignantId)
      .subscribe(
        (data) => {
          this.affectations = data.filter((affectation: Affectation) => {
            const affectationYear = new Date(affectation.dateAffectation).getFullYear();
            return affectationYear === this.selectedYear?.debut;
          });

          console.log('Filtered Affectations:', this.affectations);

          // Charger les noms des modules et groupes
          let modulesLoaded = 0;
          this.affectations.forEach((affectation, index) => {
            this.loadModuleName(affectation.moduleId || -1, () => {
              modulesLoaded++;
              // Lorsque tous les modules sont chargés, trier les affectations
              if (modulesLoaded === this.affectations.length) {
                this.sortAffectationsByModule();
              }
            });
            this.loadGroupName(affectation.groupeId);
          });
        },
        (error) => {
          console.error('Erreur lors de la récupération des affectations', error);
        }
      );
  }
  private sortAffectationsByModule(): void {
    this.affectations.sort((a, b) => {
      // @ts-ignore
      const nomA = this.nomModules[a.moduleId] || '';
      // @ts-ignore
      const nomB = this.nomModules[b.moduleId] || '';
      return nomA.localeCompare(nomB);
    });
    console.log('Affectations triées par module:', this.affectations);
  }
  loadCoAffectations(moduleId: number): void {
    if (!this.coAffectations[moduleId]) {
      this.affectationsService.getCoAffectationsByModule(moduleId).subscribe(
        (data: CoAffectation[]) => {
          this.coAffectations[moduleId] = data.filter(
            (coAffectation) =>
              coAffectation.id !==
              Number(
                this.affectations.find(
                  (affectation) => affectation.moduleId === moduleId
                )?.id
              )
          );

          this.selectedModuleId = moduleId;
        },
        (error) => {
          console.error(
            'Erreur lors de la récupération des co-affectations',
            error
          );
        }
      );
    } else {
      this.selectedModuleId =
        this.selectedModuleId === moduleId ? null : moduleId; // Permet de masquer au clic
    }
  }

  private loadModuleName(moduleId: number, callback?: () => void): void {
    if (!this.nomModules[moduleId]) {
      this.moduleService.getModuleById(moduleId).subscribe((module) => {
        this.nomModules[moduleId] = module.nom;
        if (callback) callback();
      });
    } else if (callback) {
      callback();
    }
  }

  private loadGroupName(groupeId: number): void {
    if (!this.nomGroupes[groupeId]) {
      // Éviter de charger plusieurs fois le même groupe
      this.groupeService.getGroupeById(groupeId).subscribe((groupe) => {
        this.nomGroupes[groupeId] = groupe.nom;
      });
    }
  }

  private loadEnseignantName(enseignantId: number): void {
    this.enseignantService.getEnseignant(enseignantId).subscribe(
      (enseignant: EnseignantDto) => {
        this.username = `${enseignant.firstname.charAt(0).toUpperCase()}. ${
          enseignant.name
        }`;
      },
      (error) => {
        console.error(
          'Erreur lors de la récupération du nom de l enseignant',
          error
        );
      }
    );
  }

  startEditing(affectation: any) {
    this.editingId = affectation.id;
    console.log(affectation.commentaire);
    this.editedComment = affectation.commentaire ?? '';
  }

  saveComment(affectation: any) {
    if (this.editedComment.length > 255) {
      return;
    }

    affectation.commentaire = this.editedComment;
    this._api
      .updateAffectation(affectation.id, 0, affectation.commentaire)
      .subscribe((resp) => {
        affectation.commentaire = resp.commentaire;
      });
    this.editingId = null;
  }

  cancelEditing() {
    this.editingId = null;
  }

  @Input()
  set enseignant(enseignant: EnseignantDto) {
    this.dialog = true;
    console.log('nouvel user recu ', enseignant);

    if (enseignant?.id !== undefined) {
      this.enseignantId = enseignant.id.toString();
    } else {
      console.warn("l'utilateur n'a pas d'ID");
      this.enseignantId = '';
    }

    this.loadData();
  }
}
