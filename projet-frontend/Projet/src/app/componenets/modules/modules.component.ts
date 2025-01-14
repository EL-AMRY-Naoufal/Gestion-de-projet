import {Component, OnInit} from '@angular/core';
import {MenuComponent} from '../shared/menu/menu.component';
import {Annee} from "../../types/modules.types";
import {DepartementService} from '../../services/departement.service';
import {NgForOf} from "@angular/common";
import { AnneeService } from '../../services/annee.service';
import { AnneeType } from '../shared/types/annee.type';
import { CommonModule } from '@angular/common';
import { DepartementType } from '../shared/types/departement.type';

@Component({
  selector: 'app-modules',
  standalone: true,
  imports: [
    MenuComponent,
    CommonModule,
    NgForOf,
  ],
  templateUrl: './modules.component.html',
  styleUrl: './modules.component.scss'
})
export class ModulesComponent implements OnInit {

  annees: AnneeType[] = [];
  constructor(private departementService: DepartementService, private anneeService : AnneeService) {}

  selectedItemType: string | null = null;
  selectedItem: any = null;

  ngOnInit(): void {
    //get all annees
    this.anneeService.getAllAnnees().subscribe(data => { //TODO remove nested subscribes
      this.annees = data;
      console.log(this.annees);

      //for each annee, get all departements
      this.annees.forEach(annee => {
        //send request to backend
        this.departementService.getDepartementsByYear(annee.id).subscribe(data => {
          //reassign departements to each annee
          this.annees[annee.id].departements = data;

          if(annee.departements != null) {

            //for each departement, get all formations (skip)
            annee.departements.forEach(departement => {
              if(departement.formations != null) {

                //for each formations, get all niveaux
                departement.formations.forEach(formation => {

                  //get formations //TODO faire le backend
                });
              }
            });
          }
        });
      })
    })
  }


//exemple pour tester

  data: { annees: Annee[] } = {
    annees: [
      {
        label: '2025',
        departements: [
          {
            label: 'Informatique',
            formations: [
              {
                label: 'Master',
                niveaux: [
                  {
                    label: 'M2',
                    orientations: [
                      {
                        label: 'IL',
                        semestres: [
                          {
                            label: 'S1',
                            modules: [
                              {
                                label: 'Concept Web',
                                groupes: [{ nom: 'Groupe A', heures: 20 }]
                              },
                              {
                                label: 'Service Web',
                                groupes: [{ nom: 'Groupe B', heures: 15 }]
                              }
                            ]
                          }
                        ]
                      }
                    ]
                  }
                ]
              }
            ]
          }
        ]
      }
    ]
  };

  showType(type: string, item: any) {
    console.log(`Type: ${type}`, item);
  }

  addAnnee() {
    this.data.annees.push({label: '', departements: []});
  }

  addDepartement(anneeIndex: number) {
    this.data.annees[anneeIndex].departements.push({label: '', formations: []});
  }

  addFormation(anneeIndex: number, departementIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations.push({label: '', niveaux: []});
  }

  addNiveau(anneeIndex: number, departementIndex: number, formationIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux.push({
      label: '',
      orientations: []
    });
  }

  addOrientation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations.push({
      label: '',
      semestres: []
    });
  }

  addSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres.push({
      label: '',
      modules: []
    });
  }

  addModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number, semestreIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres[semestreIndex].modules.push({
      label: '',
      groupes: []
    });
  }

  addGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number, semestreIndex: number, moduleIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres[semestreIndex].modules[moduleIndex].groupes.push({
      id: '',
      nom: '',
      heures: 0
    });
  }

  removeAnnee(index: number) {
    this.data.annees.splice(index, 1);
  }

  removeDepartement(anneeIndex: number, departementIndex: number) {
    this.data.annees[anneeIndex].departements.splice(departementIndex, 1);
  }

  removeFormation(anneeIndex: number, departementIndex: number, formationIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations.splice(formationIndex, 1);
  }

  removeNiveau(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux.splice(niveauIndex, 1);
  }

  removeOrientation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations.splice(orientationIndex, 1);
  }

  removeSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number, semestreIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres.splice(semestreIndex, 1);
  }

  removeModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number, semestreIndex: number, moduleIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres[semestreIndex].modules.splice(moduleIndex, 1);
  }

  removeGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres[semestreIndex].modules[moduleIndex].groupes.splice(groupeIndex, 1);
  }  
}
