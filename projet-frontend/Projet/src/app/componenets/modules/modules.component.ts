import {Component, OnInit} from '@angular/core';
import {MenuComponent} from '../shared/menu/menu.component';
import {Annee, Departement, Formation, Groupe, Module, Niveau, Semestre} from "../../types/modules.types";
import {DepartementService} from '../../services/departement.service';
import {NgForOf} from "@angular/common";
import { AnneeService } from '../../services/annee.service';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {AddAnneeDialogComponent} from "./dialog/add-annee-dialog/add-annee-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {AddDepartementDialogComponent} from "./dialog/add-departement-dialog/add-departement-dialog.component";
import {AddFormationDialogComponent} from "./dialog/add-formation-dialog/add-formation-dialog.component";
import {AddNiveauDialogComponent} from "./dialog/add-niveau-dialog/add-niveau-dialog.component";
import {AddSemestreDialogComponent} from "./dialog/add-semestre-dialog/add-semestre-dialog.component";
import {AddModulesDialogComponent} from "./dialog/add-modules-dialog/add-modules-dialog.component";
import {AddGroupeDialogComponent} from "./dialog/add-groupe-dialog/add-groupe-dialog.component";
import { NiveauService } from '../../services/niveau.service';
import { SemestreService } from '../../services/semestre.service';
import { GroupeService } from '../../services/groupe.service';

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

  data: { annees: Annee[] } = { annees: [] }

  constructor(
    public dialog: MatDialog, 
    private departementService: DepartementService, 
    private anneeService : AnneeService, 
    private niveauService : NiveauService, 
    private semestreService : SemestreService, 
    private groupeService : GroupeService
  ) {}



  ngOnInit(): void {
    //get all annees
    this.anneeService.getAllAnnees().subscribe(data => { //TODO remove nested subscribes
      this.data.annees = data;

      //for each annee, get all departements
      this.data.annees.forEach((annee, anneeIndex) => {
        //send request to backend
        if(annee.id != undefined) {
          let annee_id = annee.id;
          this.departementService.getDepartementsByYear(annee_id).subscribe(data => {
            //reassign departements to each annee
            this.data.annees[anneeIndex].departements = data;

            if(annee.departements != null) {

              //for each departement, get all formations (skip)
              annee.departements.forEach((departement, departementIndex) => {
                if(departement.formations != null) {

                  //for each formations, get all niveaux
                  departement.formations.forEach((formation, formationIndex) => {
                    if(formation.id != undefined) {
                      this.niveauService.getNiveauxByFormation(formation.id).subscribe(data => {
                          this.data.annees[anneeIndex].departements[departementIndex]
                          .formations[formationIndex].niveaux = data;

                          //TODO
                          //for each niveau, get all semestres
                          formation.niveaux.forEach((niveau, niveauIndex) => {
                            if(niveau.id != undefined) {
                              
                              this.semestreService.getSemestresByNiveau(niveau.id).subscribe(data => {
                                this.data.annees[anneeIndex]
                                .departements[departementIndex].formations[formationIndex]
                                .niveaux[niveauIndex].semestres = data;
                                //for each semestre, get all modules (skip)
                                niveau.semestres.forEach((semestre, semestreIndex) => {
                                  //for each module, get all groupes
                                  semestre.modules.forEach((module, moduleIndex) => {
                                    if(module.id != undefined) {
                                      this.groupeService.getGroupesByModule(module.id).subscribe(data => {
                                        this.data.annees[anneeIndex]
                                        .departements[departementIndex].formations[formationIndex]
                                        .niveaux[niveauIndex].semestres[semestreIndex]
                                        .modules[moduleIndex].groupes = data;

                                        //TODO username utilisé temporairement
                                        module.groupes.forEach((groupe, groupeId) => {
                                          groupe.affectations.forEach(affectation => affectation.nomEnseignant = affectation.enseignant?.user?.username)
                                        })
                                      })
                                    }
                                  })
                                })
                              })
                            }
                          })
                      })
                    }
                  });
                }
              });
            }
          });
        }
      })
    })
  }

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
    this.data.annees[anneeIndex].departements.push(newDepartement);
  }

  openAddFormationDialog(anneeIndex: number, departementIndex: number): void {
    const dialogRef = this.dialog.open(AddFormationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
       this.addFormation(anneeIndex,departementIndex,result);
      }
    });
  }

  addFormation(anneeIndex: number, departementIndex: number, newFormation: Formation) {
    this.data.annees[anneeIndex].departements[departementIndex].formations.push(newFormation);
  }


  openAddNiveauDialog(anneeIndex: number, departementIndex: number, formationIndex: number): void {
    const dialogRef = this.dialog.open(AddNiveauDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addNiveau(anneeIndex, departementIndex, formationIndex, result);
      }
    });
  }

  addNiveau(anneeIndex: number, departementIndex: number, formationIndex: number, newNiveau: Niveau) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux.push(newNiveau);
  }

  // openAddOrientationDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number): void {
  //   const dialogRef = this.dialog.open(AddOrientationDialogComponent);

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       this.addOrientation(anneeIndex, departementIndex, formationIndex, niveauIndex, result);
  //     }
  //   });
  // }

  // addOrientation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, newOrientation: Orientation) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations.push(newOrientation);
  // }
  openAddSemestreDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number): void {
    const dialogRef = this.dialog.open(AddSemestreDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addSemestre(anneeIndex, departementIndex, formationIndex, niveauIndex, result);
      }
    });
  }

  addSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, newSemestre: Semestre) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres.push(newSemestre);
  }

  openAddModuleDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number): void {
    const dialogRef = this.dialog.open(AddModulesDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addModule(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, result);
      }
    });
  }

  addModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, newModule: Module) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules.push(newModule);
  }


  openAddGroupeDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number): void {
    const dialogRef = this.dialog.open(AddGroupeDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addGroupe(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, moduleIndex, result);
      }
    });
  }

  addGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, newGroupe: Groupe)
  {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes.push(newGroupe);
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

  // removeOrientation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, orientationIndex: number) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].orientations.splice(orientationIndex, 1);
  // }

  removeSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres.splice(semestreIndex, 1);
  }

  removeModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules.splice(moduleIndex, 1);
  }

  removeGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes.splice(groupeIndex, 1);
  }
}
