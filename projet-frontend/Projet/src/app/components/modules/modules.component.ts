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
import { ModuleService } from '../../services/module.service';

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
    private moduleService: ModuleService,
    private groupeService: GroupeService,
    private userService: UserService,
    private formationService: FormationService,
    // private affectationService: AffectationService
    ) {
  }


  ngOnInit(): void {
    this.getAllAnnees();
    this.getAllDepartements();
    this.getAllFormations();
    this.getAllNiveaux();
    this.getAllSemestres();
    this.getAllModules();
    this.getAllGroupes();
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

  getDepartementsByAnneeIndex(anneeId : number | undefined) {
    // console.log(this.departements);
    if(anneeId != undefined) {
      return this.departements.filter((departement) => departement.anneeId === anneeId);
    }
    console.log("année non sauvegardée");
    return [];
  }

  getAllFormations() {
    this.formationService.getAllFormations().subscribe((formationsResult) => this.formations = formationsResult);
  }

  getFormationsByDepartement(departementId : number | undefined) {
    if(departementId != undefined) {
      return this.formations.filter((formation) => formation.departementId === departementId);
    }
    console.log("departement non sauvegardé");
    return [];
  }

  getAllNiveaux() {
    return this.niveauService.getAllNiveaux().subscribe((niveauResult) => this.niveaux = niveauResult);
  }

  getNiveauxByFormation(formationId: number | undefined) {
    if(formationId != undefined) {
      return this.niveaux.filter((niveau) => niveau.formationId === formationId);
    }
    console.log("formation non sauvegardée");
    return [];
  }

  getAllSemestres() {
    return this.semestreService.getAllSemestres().subscribe((semestreResult) => this.semestres = semestreResult);
  }

  getSemestreByNiveau(niveauId: number | undefined) {
    if(niveauId != undefined) {
      return this.semestres.filter((semestre) => semestre.niveauId === niveauId);
    }
    console.log("niveau non sauvegardé");
    return [];
  }

  getAllModules() {
    return this.moduleService.getAllModules().subscribe((modulesResult) => this.modules = modulesResult);
  }

  getModulesBySemestre(semestreId: number | undefined) {
    if(semestreId != undefined) {
      return this.modules.filter((module) => module.semestreId === semestreId);
    }
    console.log("semestre non sauvegardé");
    return [];
  }

  getAllGroupes() {
    return this.groupeService.getAllGroupes().subscribe((groupesResult) => this.groupes = groupesResult);
  }

  getGroupesByModule(moduleId: number | undefined) {
    if(moduleId != undefined) {
      return this.groupes.filter((groupe) => groupe.moduleId === moduleId);
    }
    console.log("module non sauvegardé");
    return  [];
  }

  showType(type: string, item: any) {
    console.log(`Type: ${type}`, item);
  }


  openAddAnneeDialog(): void {
    const dialogRef = this.dialog.open(AddAnneeDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.anneeService.saveAnnee(result).subscribe((anneeResult: Annee) => this.addAnnee(anneeResult));
      }
    });
  }

  addAnnee(newAnnee: Annee): void {
    this.annees.push(newAnnee);
  }

  openAddDepartementDialog(anneeId: number | undefined): void {
    const dialogRef = this.dialog.open(AddDepartementDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if(anneeId == undefined) {
          console.error("parent 'Annee' non sauvegardé");
        }

        //update parent id
        result.anneeId = anneeId;
        
        //save in db and update with id
        this.departementService.saveDepartement(result).subscribe((departementResult: Departement) => this.addDepartement(departementResult));
      }
    });
  }

  addDepartement(newDepartement: Departement) {
    this.departements.push(newDepartement);
  }

  openAddFormationDialog(departementId: number | undefined): void {
    const dialogRef = this.dialog.open(AddFormationDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {

        if(departementId == undefined) {
          console.error("parent 'Departement' non sauvegardé");
        }
        //update parent id
        result.departementId = departementId;
        
        console.log(result);
        //save in db and update with id
        this.formationService.saveFormation(result).subscribe((formationResult: Formation) => this.addFormation(formationResult));
      }
    });
  }

  addFormation(newFormation: Formation) {
    this.formations.push(newFormation);
  }


  openAddNiveauDialog(formationId: number | undefined): void {
    const dialogRef = this.dialog.open(AddNiveauDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if(formationId == undefined) {
          console.error("parent 'Formation' non sauvegardé");
        }

        //update parent id
        result.formationId = formationId;
        
        //save in db and update with id
        this.niveauService.saveNiveau(result).subscribe((niveauResult: Niveau) => this.addNiveau(niveauResult));
      }
    });
  }

  addNiveau(newNiveau: Niveau) {
    this.niveaux.push(newNiveau);
  }

  openAddSemestreDialog(niveauId: number | undefined): void {
    const dialogRef = this.dialog.open(AddSemestreDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if(niveauId == undefined) {
          console.error("parent 'Niveau' non sauvegardé");
        }
  
        //update parent id
        result.niveauId = niveauId;
        
        //save in db and update with id
        this.semestreService.saveSemestre(result).subscribe((semestreResult: Semestre) => this.addSemestre(semestreResult));
      }

    });
  }

  addSemestre(newSemestre: Semestre) {
    this.semestres.push(newSemestre);
  }

  openAddModuleDialog(semestreId: number | undefined): void {
    const dialogRef = this.dialog.open(AddModulesDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if(semestreId != undefined) {
          //result contains module + groupes so we have to parse it
          let module: Module = { nom: result.nom, heuresParType: result.heuresParType, semestreId: semestreId};
          this.moduleService.saveModule(module).subscribe((moduleResult: Module) => {
            if(moduleResult.id != undefined) { 
              this.addModule(moduleResult);
              result.groupes.forEach((groupe: Groupe) => {
                groupe.moduleId = moduleResult.id!;
                this.groupeService.saveGroupe(groupe).subscribe((groupeResult: Groupe) => this.addGroupe(groupeResult));
              });
            }
            else {
              console.error("Le module n'existe pas");
            }
          })
        }
      }
    });
  }

  addModule(newModule: Module) {
    this.modules.push(newModule);
  }

  
  openAddGroupeDialog(moduleId: number | undefined): void {
    const dialogRef = this.dialog.open(AddGroupeDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if(moduleId == undefined) {
          console.error("parent 'Module' non sauvegardé");
        }
  
        //update parent id
        result.moduleId = moduleId;
        
        //save in db and update with id
        this.groupeService.saveGroupe(result).subscribe((groupeResult: Groupe) => this.addGroupe(groupeResult));
      }
    });
  }

  addGroupe(newGroupe: Groupe) {
    this.groupes.push(newGroupe);
  }


  // openAddAffectationDialog(groupeId: number | undefined): void {
  //   const dialogRef = this.dialog.open(AddAffectationComponent, {
  //     data: {
  //       // moduleId: groupe.moduleId,
  //       groupeId: groupeId
  //     }
  //   });

  //   dialogRef.afterClosed().subscribe(result => {
  //     if (result) {
  //       if(groupeId == undefined) {
  //         console.error("parent 'Groupe' non sauvegardé");
  //       }
  
  //       //update parent id
  //       result.groupeId = groupeId;
        
  //       //save in db and update with id
  //       this.affectationSer.saveGroupe(result).subscribe((groupeResult: Groupe) => this.addGroupe(groupeResult));
  //     }

  //     if (result) {
  //       this.addAffectation(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, moduleIndex, groupeIndex, result);
  //     }
  //   });
  // }


  // addAffectation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number, newAffectation: any) {
  //   this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes[groupeIndex].affectations.push(newAffectation);
  // }

  // updateAffectation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number, affectationIndex: number, heuresAffectees: number) {
  //   const affectationId = this.affectations[affectationIndex].id;

  //   // @ts-ignore
  //   this.userService.updateAffectation(affectationId,heuresAffectees).subscribe({
  //     next: () => {
  //       alert('Heures affectées mis à jour');
  //     },
  //     error: (error) => {
  //       console.error('Error updating affectation:', error);
  //     }
  //   });
  // }


  removeAnnee(annee: Annee) {
    let anneeIndex = this.annees.findIndex((currentAnnee) => currentAnnee === annee);
    this.annees.splice(anneeIndex!, 1);
  }

  removeDepartement(departement: Departement) {
    let departementIndex = this.departements.findIndex((currentDepartement) => currentDepartement === departement);
    this.departements.splice(departementIndex!, 1);
  }

  removeFormation(formation: Formation) {
    let formationIndex = this.formations.findIndex((currentFormation) => currentFormation === formation);
    this.formations.splice(formationIndex, 1);
  }

  removeNiveau(niveau: Niveau) {
    let niveauIndex = this.niveaux.findIndex((currentNiveau) => currentNiveau === niveau);
    this.niveaux.splice(niveauIndex, 1);
  }

  removeSemestre(semestre : Semestre) {
    let semestreIndex = this.semestres.findIndex((currentSemestre) => currentSemestre === semestre);
    this.semestres.splice(semestreIndex, 1);
  }

  removeModule(module: Module) {
    let moduleIndex = this.modules.findIndex((currentModule) => currentModule === module);
    this.modules.splice(moduleIndex, 1);
  }

  removeGroupe(groupe: Groupe) {
    let groupeIndex = this.groupes.findIndex((currentGroupe) => currentGroupe == groupe);
    this.groupes.splice(groupeIndex, 1);
  }

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
