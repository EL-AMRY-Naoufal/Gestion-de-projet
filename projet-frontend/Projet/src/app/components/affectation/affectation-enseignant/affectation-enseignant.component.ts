import { Component, input, Input, OnInit } from '@angular/core';
import { EnseignantService } from '../../../services/enseignant.service';
import {NgForOf, NgIf} from "@angular/common";
import {LoginService} from "../../../services/login.service";
import { User } from '../../shared/types/user.type';
import {ActivatedRoute} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import {ApiService} from '../../../services/api-service';

import {Affectation, CoAffectation} from "../../shared/types/modules.types";
import {EnseignantDto} from "../../shared/types/enseignant.type";
import {GroupeService} from "../../../services/groupe.service";
import {ModuleService} from "../../../services/module.service";
import {AffectationService} from "../../../services/affectation.service";


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
    FormsModule
  ],
  styleUrls: ['affectation-enseignant.component.scss']
})
export class AffectationListComponent implements OnInit {

  affectations: Affectation[] = [];
  nomGroupes: { [key: number]: string } = {};
  nomModules: { [key: number]: string } = {};
  coAffectations: { [key: number]: CoAffectation[] } = {};
  selectedModuleId: number | null = null;

  @Input() dialog: boolean = false;  // Input to receive enseignantId from the parent (dialog)
  connect : boolean = false; // Si on utilise l'utilisateur connecter

  affectations: AffectationType[] = [];

  enseignantId!: string;
  username: string = '';

  editingId: number | null = null;
  editedComment: string = '';


  constructor(private enseignantService: EnseignantService,
              private loginService: LoginService,
              private affectationsService: AffectationService,
              private authService: ApiService,
              private activatedRoute: ActivatedRoute,
              private groupeService: GroupeService,
              private moduleService: ModuleService,
              private _api: ApiService) {

  }

  ngOnInit(): void {
    this.initEnseignant();
  }

  private initEnseignant(): void {
    const enseignantIdFromRoute = this.activatedRoute.snapshot.paramMap.get('id');

    if (enseignantIdFromRoute) {
      this.setEnseignantIdAndLoadData(enseignantIdFromRoute);
    } else {
      this.fetchEnseignantIdFromUser();
    }
  }


  private setEnseignantIdAndLoadData(enseignantId: string): void {
    this.enseignantId = enseignantId;
    this.loadAffectations();
    this.loadEnseignantName(Number(enseignantId));
  }

  private fetchEnseignantIdFromUser(): void {
    this.enseignantService.getEnseignantByUserId(this.loginService.connectUser())
      .subscribe((enseignant: EnseignantDto) => {
        if (enseignant.id) {
          this.setEnseignantIdAndLoadData(enseignant.id.toString());
        }
      }, (error) => {
        console.error('Erreur lors de la récupération de l enseignant', error);
      });
  }

  private loadAffectations(): void {
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
  }

  loadCoAffectations(moduleId: number): void {
    if (!this.coAffectations[moduleId]) {
      this.affectationsService.getCoAffectationsByModule(moduleId).subscribe(
        (data: CoAffectation[]) => {
          this.coAffectations[moduleId] = data.filter(
            (coAffectation) => coAffectation.id !== Number(this.affectations.find(affectation => affectation.moduleId === moduleId)?.id)
          );

          this.selectedModuleId = moduleId;
        },
        (error) => {
          console.error('Erreur lors de la récupération des co-affectations', error);
        }
      );
    } else {
      this.selectedModuleId = this.selectedModuleId === moduleId ? null : moduleId; // Permet de masquer au clic
    }
  }

  private loadModuleName(moduleId: number): void {
    if (!this.nomModules[moduleId]) { // Éviter de charger plusieurs fois le même module
      this.moduleService.getModuleById(moduleId).subscribe((module) => {
        this.nomModules[moduleId] = module.nom;
      });
    }
  }

  private loadGroupName(groupeId: number): void {
    if (!this.nomGroupes[groupeId]) { // Éviter de charger plusieurs fois le même groupe
      this.groupeService.getGroupeById(groupeId).subscribe((groupe) => {
        this.nomGroupes[groupeId] = groupe.nom;
      });
    }
  }


  private loadEnseignantName(enseignantId: number): void {
    this.enseignantService.getEnseignant(enseignantId)
      .subscribe((enseignant: EnseignantDto) => {
        this.username = `${enseignant.firstname.charAt(0).toUpperCase()}. ${enseignant.name}`;
      }, (error) => {
        console.error('Erreur lors de la récupération du nom de l enseignant', error);
      });
  }



  startEditing(affectation: any) {
    this.editingId = affectation.id;
    this.editedComment = affectation.commentaire;
  }

  saveComment(affectation: any) {

    if (this.editedComment.length > 255) {
      return;
    }

    affectation.commentaire = this.editedComment;
    this._api.updateAffectation(affectation.id, 0, affectation.commentaire).subscribe((resp) => {
      affectation.commentaire = resp.commentaire;
    });
    this.editingId = null;
  }

  cancelEditing() {
    this.editingId = null;
  }


  @Input()
  set user(user: User) {
    console.log("Nouvel user reçu :", user);

    if (user?.id !== undefined) {
      this.enseignantId = user.id.toString();
    } else {
      console.warn("L'utilisateur n'a pas d'ID !");
      this.enseignantId = ''; // Ou une valeur par défaut
    }
  }



}
