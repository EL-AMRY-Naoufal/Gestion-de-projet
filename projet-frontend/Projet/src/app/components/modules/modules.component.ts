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
import { EnseignantService } from '../../services/enseignant.service';
import { AffectationService } from '../../services/affectation.service';
import { EnseignantDto } from '../shared/types/enseignant.type';
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";
import { ConfirmDeletionDialogComponent } from './dialog/confirm-deletion-dialog/confirm-deletion-dialog.component';
import {User} from "../shared/types/user.type";
import {AffectationDialogComponent} from "../affectation/affectation-dialog/affectation-dialog.component";


@Component({
  selector: 'app-modules',
  standalone: true,
  imports: [
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
    private formationService: FormationService,
    private enseignantService: EnseignantService,
    private userService: UserService,
    private affectationService: AffectationService,
    private router: Router,
  private _dialog: MatDialog

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
    this.getAllAffectations();
  }

  getAllAnnees() {
    this.anneeService.getAllAnnees().subscribe((anneesResult) => this.annees = anneesResult);
  }

  getAllDepartements() {
    this.departementService.getAllDepartements().subscribe((departementsResult) => this.departements = departementsResult);
  }

  getDepartementsByAnneeIndex(anneeId : number | undefined) {
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

  getAllAffectations() {
    return this.affectationService.getAllAffectations().subscribe((affectationsResult) => this.affectations = affectationsResult);
  }

  getAffectationsByGroupe(groupeId: number | undefined) {
    let affectationsResult : Affectation[] = [];
    if(groupeId == undefined) {
      console.log("groupe non sauvegardé");
    }
    else {
      affectationsResult = this.affectations.filter((affectation) => affectation.groupeId === groupeId);
    }

    return affectationsResult;
  }

  nomEnseignantMap: Map<number, string> = new Map();

  getNomEnseignantById(enseignantId: number | undefined): string {
    if (!enseignantId) return "";

    // Vérifier si l'enseignant est déjà chargé
    if (this.nomEnseignantMap.has(enseignantId)) {
      return this.nomEnseignantMap.get(enseignantId)!;
    }

    // Lancer la requête si l'enseignant n'est pas encore chargé
    this.enseignantService.getEnseignant(enseignantId).subscribe((enseignantResult: EnseignantDto) => {
      if (enseignantResult.firstname && enseignantResult.name) {
        const firstInitial = enseignantResult.firstname.charAt(0).toUpperCase();
        const lastName = enseignantResult.name.charAt(0).toUpperCase() + enseignantResult.name.slice(1).toLowerCase();
        this.nomEnseignantMap.set(enseignantId, `${firstInitial}. ${lastName}`);
      }
    });

    return "";
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
    if (moduleId == undefined) {
      console.error("Parent 'Module' non sauvegardé");
      return;
    }

    // Récupérer le module pour obtenir ses heures
    const module = this.modules.find(m => m.id === moduleId);
    if (!module) {
      console.error("Module introuvable");
      return;
    }

    const dialogRef = this.dialog.open(AddGroupeDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {

        //update parent id
        result.moduleId = moduleId;
        // @ts-ignore
        result.totalHeuresDuGroupe = module.heuresParType[result.type];
        this.groupeService.saveGroupe(result).subscribe((groupeResult: Groupe) => this.addGroupe(groupeResult));
      }
    });
  }

  addGroupe(newGroupe: Groupe) {
    this.groupes.push(newGroupe);
  }


  openAddAffectationDialog(groupeId: number | undefined): void {
    const dialogRef = this.dialog.open(AddAffectationComponent, {
      data: {
        groupeId: groupeId
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if(groupeId == undefined) {
          console.error("parent 'Groupe' non sauvegardé");
        }

        //update parent id
        result.groupeId = groupeId;

        //save in db and update with id
        this.affectationService.saveAffectation(result).subscribe((affectationResult: Affectation) => this.addAffectation(affectationResult));
      }
    });
  }


  addAffectation(newAffectation: Affectation) {
    this.getAllGroupes();
    this.affectations.push(newAffectation);
  }

  updateAffectation(affectation: Affectation) {
    if (!affectation.id) {
      console.error("Affectation ID is undefined");
      return;
    }

    this.userService.updateAffectation(affectation.id, affectation.heuresAssignees).subscribe({
      next: () => {
        alert('Heures affectées mises à jour');
        this.getAllGroupes();
      }
    });
  }





  removeAnnee(annee: Annee) {
    let departementsChildren: Departement | undefined = this.departements.find((currentDepartement) => currentDepartement.anneeId === annee.id);
    if(departementsChildren == undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          let anneeIndex = this.annees.findIndex((currentAnnee) => currentAnnee === annee);
          this.annees.splice(anneeIndex!, 1);
          this.anneeService.deleteAnnee(annee).subscribe();
        }
      })
    }
    else {
      alert("Impossible de supprimer cette année : elle contient encore des départements");
    }
  }

  removeDepartement(departement: Departement) {
    let formationsChildren: Formation | undefined = this.formations.find((currentFormation) => currentFormation.departementId === departement.id);
    if(formationsChildren == undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          let departementIndex = this.departements.findIndex((currentDepartement) => currentDepartement === departement);
          this.departements.splice(departementIndex!, 1);
          this.departementService.deleteDepartement(departement).subscribe();
        }
      })
    }
    else {
      alert("Impossible de supprimer ce département : il contient encore des formations");
    }
  }

  removeFormation(formation: Formation) {
    let niveauxChildren: Niveau | undefined = this.niveaux.find((currentNiveau) => currentNiveau.formationId === formation.id);
     if(niveauxChildren == undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          let formationIndex = this.formations.findIndex((currentFormation) => currentFormation === formation);
          this.formations.splice(formationIndex, 1);
          this.formationService.deleteFormation(formation).subscribe();
        }
      })
    }
    else {
      alert("Impossible de supprimer cette formation : elle contient encore des niveaux");
    }
  }

  removeNiveau(niveau: Niveau) {
    let semestresChildren: Semestre | undefined = this.semestres.find((currentSemestre) => currentSemestre.niveauId === niveau.id);
    if(semestresChildren == undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          let niveauIndex = this.niveaux.findIndex((currentNiveau) => currentNiveau === niveau);
          this.niveaux.splice(niveauIndex, 1);
          this.niveauService.deleteNiveau(niveau).subscribe();
        }
      })
    }
    else {
      alert("Impossible de supprimer ce niveau : il contient encore des semestres");
    }
  }

  removeSemestre(semestre : Semestre) {
    let modulesChildren: Module | undefined = this.modules.find((currentModule) => currentModule.semestreId === semestre.id);
    if(modulesChildren == undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          let semestreIndex = this.semestres.findIndex((currentSemestre) => currentSemestre === semestre);
          this.semestres.splice(semestreIndex, 1);
          this.semestreService.deleteSemestre(semestre).subscribe();
        }
      })
    }
    else {
      alert("Impossible de supprimer ce semestre : il contient encore des modules");
    }
  }

  removeModule(module: Module) {
    let groupesChildren: Groupe | undefined = this.groupes.find((currentGroupe) => currentGroupe.moduleId === module.id);
    if(groupesChildren == undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          let moduleIndex = this.modules.findIndex((currentModule) => currentModule === module);
          this.modules.splice(moduleIndex, 1);
          this.moduleService.deleteModule(module).subscribe();
        }
      })
    }
    else {
      alert("Impossible de supprimer ce module : il contient encore des groupes");
    }
  }

  removeGroupe(groupe: Groupe) {
    let affectationsChildren: Affectation | undefined = this.affectations.find((currentAffectation) => currentAffectation.groupeId === groupe.id);
    if(affectationsChildren == undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          let groupeIndex = this.groupes.findIndex((currentGroupe) => currentGroupe === groupe);
          this.groupes.splice(groupeIndex, 1);
          this.groupeService.deleteGroupe(groupe).subscribe();
        }
      })
    }
    else {
      alert("Impossible de supprimer ce module : il contient encore des groupes");
    }

  }

  removeAffectation(affectation: Affectation) {
    let affectationIndex = this.affectations.findIndex((currentAffectation) => currentAffectation === affectation);

    if (affectation.id != undefined) {
      const dialogRef = this.dialog.open(ConfirmDeletionDialogComponent);
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          this.userService.deleteAffectation(affectation.id!).subscribe({
            next: () => {
              console.log('Affectation deleted');
              this.affectations.splice(affectationIndex, 1);
              let groupeIndex = this.groupes.findIndex((groupe) => groupe.id === affectation.groupeId);
              this.groupes[groupeIndex].heuresAffectees -= affectation.heuresAssignees;
            }
          });
        }
      })

    } else {
      console.error("Affectation ID is undefined");
    }
  }
  navigateToAffectations(user: number ) {
    console.log("affectation");

    if (!user) {
      console.warn('Aucun utilisateur défini');
      return;
    }

    // open modal
    this._dialog.open(AffectationDialogComponent, {
      disableClose: true,
      panelClass: 'custom-dialog-container',
      data: { enseignantId: user },
      width: '90vw',
      height: '70vh',
      maxWidth: '100vw'
    });
  }
}
