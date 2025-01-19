import {Component, OnInit} from '@angular/core';
import {MenuComponent} from '../shared/menu/menu.component';
import {Annee, Departement} from "../../types/modules.types";
import {DepartementService} from '../../services/departement.service';
import {NgForOf} from "@angular/common";
import { AnneeService } from '../../services/annee.service';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {AddAnneeDialogComponent} from "./dialog/add-annee-dialog/add-annee-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {AddDepartementDialogComponent} from "./dialog/add-departement-dialog/add-departement-dialog.component";

@Component({
  selector: 'app-modules',
  standalone: true,
  imports: [
    MenuComponent,
    CommonModule,
    NgForOf,
    FormsModule,
  ],
  templateUrl: './modules.component.html',
  styleUrl: './modules.component.scss'
})
export class ModulesComponent implements OnInit {

  // les boolean pour afficher les dialogues
  showAddAnneeDialog: boolean = false;
  showAddDepartementDialog: boolean = false;

  newAnnee: Annee = { debut: '', departements: [] };
  newDepartement: Departement = { nom: '', responsableDeDepartement: '', formations: [] };

  annees: Annee[] = [];

  constructor(public dialog: MatDialog,private departementService: DepartementService, private anneeService : AnneeService) {}



  ngOnInit(): void {




    //get all annees
    this.anneeService.getAllAnnees().subscribe(data => { //TODO remove nested subscribes
      this.annees = data;
      console.log(this.annees);

      //for each annee, get all departements
      this.annees.forEach(annee => {
        //send request to backend
        if(annee.id != undefined) {
          let annee_id = annee.id;
          this.departementService.getDepartementsByYear(annee_id).subscribe(data => {
            //reassign departements to each annee
            this.annees[annee_id].departements = data;

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
        }
      })
    })
  }


//exemple pour tester

  data: { annees: Annee[] } = {
    annees: [
      {
        id: 0,
        debut: '2025',
        departements: [
          {
            id: 1,
            nom: 'Informatique',
            responsableDeDepartement: 'RDD',
            formations: [
              {
                id: 1,
                nom: 'Master',
                totalHeures: 10,
                responsableFormation: "RDF",
                modules: [],
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
                                nom: 'Concept Web',
                                groupes: [{ nom: 'Groupe A', heures: 20 }],
                                totalHeuresRequises: 0,
                                heuresParType: new Map([
                                  ["CM", 10],
                                  ["TD", 10],
                                  ["TP", 10],
                                ])
                              },
                              {
                                nom: 'Service Web',
                                groupes: [{ nom: 'Groupe B', heures: 15 }],
                                totalHeuresRequises: 0,
                                heuresParType: new Map([
                                  ["CM", 10],
                                  ["TD", 10],
                                  ["TP", 10],
                                ])
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


  openAddAnneeDialog(): void {
    const dialogRef = this.dialog.open(AddAnneeDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addAnnee(result);
      }
    });
  }

  addAnnee(newAnnee : Annee): void {
    this.data.annees.push(newAnnee);
  }

  openAddDepartementDialog(anneeIndex: number): void {
    const dialogRef = this.dialog.open(AddDepartementDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addDepartement(anneeIndex,result);
      }
    });
  }

  addDepartement(anneeIndex: number, newDepartement: Departement) {
    console.log("long ", this.data.annees);
    this.data.annees[anneeIndex].departements.push(newDepartement);
  }


  addFormation(anneeIndex: number, departementIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations.push({
      nom: '',
      totalHeures: 0,
      responsableFormation: '',
      modules: [],
      niveaux: [],
    });
  }

  addNiveau(anneeIndex: number, departementIndex: number, formationIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux.push({
      label: '',
      orientations: [],
    });
  }

  addOrientation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations.push({
      label: '',
      semestres: [],
    });
  }

  addSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres.push({
      label: '',
      modules: [],
    });
  }

  addModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number, semestreIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres[semestreIndex].modules.push({
      nom: '',
      totalHeuresRequises: 0,
      heuresParType: new Map([
        ["CM", 10],
        ["TD", 10],
        ["TP", 10],
      ]),
      groupes: [],
    });
  }

  addGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number, semestreIndex: number, moduleIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations[orientationIndex].semestres[semestreIndex].modules[moduleIndex].groupes.push({
      id: '',
      nom: '',
      heures: 0,
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
