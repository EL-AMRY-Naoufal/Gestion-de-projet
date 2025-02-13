import {Component, OnInit} from '@angular/core';
import {MenuComponent} from '../shared/menu/menu.component';
import {DepartementService} from '../../services/departement.service';
import {NgForOf} from "@angular/common";
import {AnneeService} from '../../services/annee.service';
import {CommonModule} from '@angular/common';
import {FormsModule} from "@angular/forms";
import {AddAnneeDialogComponent} from "./dialog/add-annee-dialog/add-annee-dialog.component";
import {AddAffectationComponent} from "./dialog/add-affectation/add-affectation.component";
import {MatDialog} from "@angular/material/dialog";
import {AddDepartementDialogComponent} from "./dialog/add-departement-dialog/add-departement-dialog.component";
import {AddFormationDialogComponent} from "./dialog/add-formation-dialog/add-formation-dialog.component";
import {AddNiveauDialogComponent} from "./dialog/add-niveau-dialog/add-niveau-dialog.component";
import {AddSemestreDialogComponent} from "./dialog/add-semestre-dialog/add-semestre-dialog.component";
import {AddModulesDialogComponent} from "./dialog/add-modules-dialog/add-modules-dialog.component";
import {AddGroupeDialogComponent} from "./dialog/add-groupe-dialog/add-groupe-dialog.component";
import {NiveauService} from '../../services/niveau.service';
import {SemestreService} from '../../services/semestre.service';
import {GroupeService} from '../../services/groupe.service';
import {Annee, Departement, Formation, Niveau, Semestre, Groupe, Module, Affectation} from '../shared/types/modules.types';
import {UserService} from "../../services/user.service";
import { FormationService } from '../../services/formation.service';

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

  annees: Annee[] = [];
  departements: Departement[] = [];
  formations: Formation[] = [];
  niveaux: Niveau[] = [];
  semestres: Semestre[] = [];
  modules: Module[] = [];
  groupes: Groupe[] = [];
  affectations: Affectation[] = [];

  constructor(
    public dialog: MatDialog,
    private departementService: DepartementService,
    private anneeService: AnneeService,
    private niveauService: NiveauService,
    private semestreService: SemestreService,
    private groupeService: GroupeService,
    private userService: UserService,
    private formationService: FormationService,
    ) {
  }


  ngOnInit(): void {
    this.getAllAnnees();
    this.getAllDepartements();
    this.getAllFormations();
    //get all annees
    // this.anneeService.getAllAnnees().subscribe(data => { //TODO remove nested subscribes
    //   this.data.annees = data;

    //   //for each annee, get all departements
    //   this.data.annees.forEach((annee, anneeIndex) => {
    //     //send request to backend
    //     if (annee.id != undefined) {
    //       let annee_id = annee.id;
    //       this.departementService.getDepartementsByYear(annee_id).subscribe(data => {
    //         console.log(data);
    //         //reassign departements to each annee
    //         this.data.annees[anneeIndex].departements = data;

    //         if (annee.departements != null) {

    //           //for each departement, get all formations (skip)
    //           annee.departements.forEach((departement, departementIndex) => {
                
    //             if (departement.formations != null) {

    //               //for each formations, get all niveaux
    //               departement.formations.forEach((formation, formationIndex) => {
    //                 if (formation.id != undefined) {
    //                   this.niveauService.getNiveauxByFormation(formation.id).subscribe(data => {
    //                     this.data.annees[anneeIndex].departements[departementIndex]
    //                       .formations[formationIndex].niveaux = data;

    //                     //TODO
    //                     //for each niveau, get all semestres
    //                     formation.niveaux.forEach((niveau, niveauIndex) => {
    //                       if (niveau.id != undefined) {

    //                         this.semestreService.getSemestresByNiveau(niveau.id).subscribe(data => {
    //                           this.data.annees[anneeIndex]
    //                             .departements[departementIndex].formations[formationIndex]
    //                             .niveaux[niveauIndex].semestres = data;
    //                           //for each semestre, get all modules (skip)
    //                           niveau.semestres.forEach((semestre, semestreIndex) => {
    //                             //for each module, get all groupes
    //                             semestre.modules.forEach((module, moduleIndex) => {
    //                               if (module.id != undefined) {
    //                                 this.groupeService.getGroupesByModule(module.id).subscribe(data => {
    //                                   this.data.annees[anneeIndex]
    //                                     .departements[departementIndex].formations[formationIndex]
    //                                     .niveaux[niveauIndex].semestres[semestreIndex]
    //                                     .modules[moduleIndex].groupes = data;

    //                                   //TODO username utilisé temporairement
    //                                   module.groupes.forEach((groupe, groupeId) => {
    //                                     groupe.affectations.forEach(affectation => affectation.nomEnseignant = affectation.enseignant?.user?.username)
    //                                   })
    //                                 })
    //                               }
    //                             })
    //                           })
    //                         })
    //                       }
    //                     })
    //                   })
    //                 }
    //               });
    //             }
    //           });
    //         }
    //       });
    //     }
    //   })
    // })
  }

  getAllAnnees() {
    this.anneeService.getAllAnnees().subscribe((anneesResult) => this.annees = anneesResult);
  }

  getAllDepartements() {
    this.departementService.getAllDepartements().subscribe((departementsResult) => this.departements = departementsResult);
  }

  getDepartementsByAnneeIndex(anneeIndex : number) {
    return this.departements.filter((departement) => departement.anneeId === this.annees[anneeIndex].id);
  }

  getAllFormations() {
    this.formationService.getAllFormations().subscribe((formationsResult) => this.formations = formationsResult);
  }

  getFormationsByDepartement(departementIndex : number) {
    return this.formations.filter((formation) => formation.departementId === this.departements[departementIndex].id);
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

  addAnnee(newAnnee: Annee): void {
    this.annees.push(newAnnee);
  }

  openAddDepartementDialog(anneeIndex: number): void {
    const dialogRef = this.dialog.open(AddDepartementDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        result.responsableDepartementId = 1;
        this.addDepartement(result);
      }
    });
  }

  addDepartement(newDepartement: Departement) {
    this.departements.push(newDepartement);
  }

  openAddFormationDialog(anneeIndex: number, departementIndex: number): void {
    const dialogRef = this.dialog.open(AddFormationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addFormation(result);
      }
    });
  }

  addFormation(newFormation: Formation) {
    this.formations.push(newFormation);
  }


  // openAddNiveauDialog(anneeIndex: number, departementIndex: number, formationIndex: number): void {
  //   const dialogRef = this.dialog.open(AddNiveauDialogComponent);

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       this.addNiveau(anneeIndex, departementIndex, formationIndex, result);
  //     }
  //   });
  // }

  // addNiveau(anneeIndex: number, departementIndex: number, formationIndex: number, newNiveau: Niveau) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux.push(newNiveau);
  // }

  // openAddSemestreDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number): void {
  //   const dialogRef = this.dialog.open(AddSemestreDialogComponent);

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       this.addSemestre(anneeIndex, departementIndex, formationIndex, niveauIndex, result);
  //     }
  //   });
  // }

  // addSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, newSemestre: Semestre) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres.push(newSemestre);
  // }

  // openAddModuleDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number): void {
  //   const dialogRef = this.dialog.open(AddModulesDialogComponent);

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       this.addModule(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, result);
  //     }
  //   });
  // }

  // addModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, newModule: Module) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules.push(newModule);
  // }


  // openAddGroupeDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number): void {
  //   const dialogRef = this.dialog.open(AddGroupeDialogComponent);

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       this.addGroupe(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, moduleIndex, result);
  //     }
  //   });
  // }

  // addGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, newGroupe: Groupe) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes.push(newGroupe);
  // }


  // openAddAffectationDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number): void {
  //   const dialogRef = this.dialog.open(AddAffectationComponent, {
  //     data: {
  //       moduleId: this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].id,
  //       groupeId: this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes[groupeIndex].id
  //     }
  //   });

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       this.addAffectation(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, moduleIndex, groupeIndex, result);
  //     }
  //   });
  // }


  // addAffectation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number, newAffectation: any) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes[groupeIndex].affectations.push(newAffectation);
  // }

  removeAnnee(anneeIndex: number) {
    this.annees.splice(anneeIndex, 1);
  }

  removeDepartement(departementIndex: number) {
    this.departements.splice(departementIndex, 1);
  }

  removeFormation(formationIndex: number) {
    this.formations.splice(formationIndex, 1);
  }

  // removeNiveau(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux.splice(niveauIndex, 1);
  // }


  // removeSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres.splice(semestreIndex, 1);
  // }

  // removeModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules.splice(moduleIndex, 1);
  // }

  // removeGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes.splice(groupeIndex, 1);
  // }

  // removeAffectation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number, affectationIndex: number) {
  //   const affectationId = this.data.annees[anneeIndex]
  //     .departements[departementIndex].formations[formationIndex]
  //     .niveaux[niveauIndex].semestres[semestreIndex]
  //     .modules[moduleIndex].groupes[groupeIndex]
  //     .affectations[affectationIndex].id;

  //   // @ts-ignore
  //   this.userService.deleteAffectation(affectationId).subscribe({
  //     next: () => {
  //       console.log('Affectation deleted');
  //       this.data.annees[anneeIndex]
  //         .departements[departementIndex].formations[formationIndex]
  //         .niveaux[niveauIndex].semestres[semestreIndex]
  //         .modules[moduleIndex].groupes[groupeIndex]
  //         .affectations.splice(affectationIndex, 1);

  //     },
  //     error: (error) => {
  //       console.error('Error deleting affectation:', error);
  //     }
  //   });

  // }

  // saveAnnee() {
  //   let annees_to_send = this.data.annees;
  //   //save each "annee"
  //   annees_to_send.forEach(annee => {
  //     //copying departements before it gets deleted
  //     let departements_to_send = annee.departements;
  //     this.anneeService.saveAnnee(annee).subscribe(response => console.log(response));
  //     //save each "departement"
  //     departements_to_send.forEach(departement => {
  //       departement.annee = annee;
  //       console.log("saving departement");
  //       console.log(departement);
  //       this.departementService.saveDepartement(departement).subscribe(response => console.log(response));
  //     })
  //   })
  // }
}
