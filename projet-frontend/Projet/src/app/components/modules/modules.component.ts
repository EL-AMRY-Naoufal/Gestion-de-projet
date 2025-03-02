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
import {Annee, Departement, Formation, Niveau, Semestre, Groupe, Module} from '../shared/types/modules.types';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import { User } from '../shared/types/user.type';
import { AffectationDialogComponent } from '../affectation/affectation-dialog/affectation-dialog.component';

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


  data: { annees: Annee[] } = {annees: []}

  constructor(
    public dialog: MatDialog,
    private departementService: DepartementService,
    private anneeService: AnneeService,
    private niveauService: NiveauService,
    private semestreService: SemestreService,
    private groupeService: GroupeService,
    private userService: UserService,
    private router: Router,
    private _dialog: MatDialog
  ) {
  }


  ngOnInit(): void {
    //get all annees
    this.anneeService.getAllAnnees().subscribe(data => { //TODO remove nested subscribes
      this.data.annees = data;

      //for each annee, get all departements
      this.data.annees.forEach((annee, anneeIndex) => {
        //send request to backend
        if (annee.id != undefined) {
          let annee_id = annee.id;
          this.departementService.getDepartementsByYear(annee_id).subscribe(data => {
            //reassign departements to each annee
            this.data.annees[anneeIndex].departements = data;

            if (annee.departements != null) {

              //for each departement, get all formations (skip)
              annee.departements.forEach((departement, departementIndex) => {
                if (departement.formations != null) {

                  //for each formations, get all niveaux
                  departement.formations.forEach((formation, formationIndex) => {
                    if (formation.id != undefined) {
                      this.niveauService.getNiveauxByFormation(formation.id).subscribe(data => {
                        this.data.annees[anneeIndex].departements[departementIndex]
                          .formations[formationIndex].niveaux = data;


                        //for each niveau, get all semestres
                        formation.niveaux.forEach((niveau, niveauIndex) => {
                          if (niveau.id != undefined) {

                            this.semestreService.getSemestresByNiveau(niveau.id).subscribe(data => {
                              this.data.annees[anneeIndex]
                                .departements[departementIndex].formations[formationIndex]
                                .niveaux[niveauIndex].semestres = data;
                              //for each semestre, get all modules (skip)
                              niveau.semestres.forEach((semestre, semestreIndex) => {
                                //for each module, get all groupes
                                semestre.modules.forEach((module, moduleIndex) => {
                                  if (module.id != undefined) {
                                    this.groupeService.getGroupesByModule(module.id).subscribe(data => {
                                      this.data.annees[anneeIndex]
                                        .departements[departementIndex].formations[formationIndex]
                                        .niveaux[niveauIndex].semestres[semestreIndex]
                                        .modules[moduleIndex].groupes = data;

                                      // Formater le nom sous la forme par exemple "E. Jeandel"
                                      module.groupes.forEach((groupe, groupeId) => {
                                        groupe.affectations.forEach(affectation => {
                                          if (affectation.enseignant?.user?.firstname && affectation.enseignant?.user?.name) {
                                            const firstInitial = affectation.enseignant.user.firstname.charAt(0).toUpperCase(); // Première lettre du prénom
                                            const lastName = affectation.enseignant.user.name.charAt(0).toUpperCase() + affectation.enseignant.user.name.slice(1).toLowerCase(); // Nom avec la première lettre en majuscule
                                            affectation.nomEnseignant = `${firstInitial}. ${lastName}`;
                                          } else {
                                            affectation.nomEnseignant = ''; // Si les données sont absentes, éviter les erreurs
                                          }
                                        });
                                      });

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

  navigateToAffectations(user: User |undefined)  {
     console.log("affectation");

     if (!user) {
      console.warn('Aucun utilisateur défini');
      return; // Ou redirige vers une autre page / afficher un message d'erreur
    }

    // open modal
      this._dialog.open(AffectationDialogComponent, {
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
      data: { user: user } // Passer l'enseignant en tant que donnée
    });
  }


  showType(type: string, item: any) {
    console.log(`Type: ${type}`, item);
  }


  openAddAnneeDialog(): void {
    const dialogRef = this.dialog.open(AddAnneeDialogComponent,{
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
    }
    );

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addAnnee(result);
      }
    });
  }

  addAnnee(newAnnee: Annee): void {
    this.data.annees.push(newAnnee);
  }

  openAddDepartementDialog(anneeIndex: number): void {
    const dialogRef = this.dialog.open(AddDepartementDialogComponent,{
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
    }
    );

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addDepartement(anneeIndex, result);
      }
    });
  }

  addDepartement(anneeIndex: number, newDepartement: Departement) {
    this.data.annees[anneeIndex].departements.push(newDepartement);
  }

  openAddFormationDialog(anneeIndex: number, departementIndex: number): void {
    const dialogRef = this.dialog.open(AddFormationDialogComponent,{
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
    }
    );

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addFormation(anneeIndex, departementIndex, result);
      }
    });
  }

  addFormation(anneeIndex: number, departementIndex: number, newFormation: Formation) {
    this.data.annees[anneeIndex].departements[departementIndex].formations.push(newFormation);
  }


  openAddNiveauDialog(anneeIndex: number, departementIndex: number, formationIndex: number): void {
    const dialogRef = this.dialog.open(AddNiveauDialogComponent,{
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
    }
    );

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addNiveau(anneeIndex, departementIndex, formationIndex, result);
      }
    });
  }

  addNiveau(anneeIndex: number, departementIndex: number, formationIndex: number, newNiveau: Niveau) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux.push(newNiveau);
  }

  openAddSemestreDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number): void {
    const dialogRef = this.dialog.open(AddSemestreDialogComponent,{
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
    }
    );

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
    const dialogRef = this.dialog.open(AddModulesDialogComponent,{
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
    });

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
    const dialogRef = this.dialog.open(AddGroupeDialogComponent,{
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
    }
    );

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addGroupe(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, moduleIndex, result);
      }
    });
  }

  addGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, newGroupe: Groupe) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes.push(newGroupe);
  }


  openAddAffectationDialog(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number): void {
    const dialogRef = this.dialog.open(AddAffectationComponent, {
      disableClose: true,
      panelClass: 'custom-dialog-container', // Ajouter une classe personnalisée
      data: {
        moduleId: this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].id,
        groupeId: this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes[groupeIndex].id
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.addAffectation(anneeIndex, departementIndex, formationIndex, niveauIndex, semestreIndex, moduleIndex, groupeIndex, result);
      }
    });
  }


  addAffectation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number, newAffectation: any) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes[groupeIndex].affectations.push(newAffectation);
  }

  updateAffectation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number, affectationIndex: number, heuresAffectees: number) {
    const affectationId = this.data.annees[anneeIndex]
      .departements[departementIndex].formations[formationIndex]
      .niveaux[niveauIndex].semestres[semestreIndex]
      .modules[moduleIndex].groupes[groupeIndex]
      .affectations[affectationIndex].id;

    // @ts-ignore
    this.userService.updateAffectation(affectationId,heuresAffectees).subscribe({
      next: () => {
        alert('Heures affectées mis à jour');
      },
      error: (error) => {
        console.error('Error updating affectation:', error);
      }
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


  removeSemestre(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres.splice(semestreIndex, 1);
  }

  removeModule(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules.splice(moduleIndex, 1);
  }

  removeGroupe(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number) {
    this.data.annees[anneeIndex].departements[departementIndex].formations[formationIndex].niveaux[niveauIndex].semestres[semestreIndex].modules[moduleIndex].groupes.splice(groupeIndex, 1);
  }

  removeAffectation(anneeIndex: number, departementIndex: number, formationIndex: number, niveauIndex: number, semestreIndex: number, moduleIndex: number, groupeIndex: number, affectationIndex: number) {
    const affectationId = this.data.annees[anneeIndex]
      .departements[departementIndex].formations[formationIndex]
      .niveaux[niveauIndex].semestres[semestreIndex]
      .modules[moduleIndex].groupes[groupeIndex]
      .affectations[affectationIndex].id;

    // @ts-ignore
    this.userService.deleteAffectation(affectationId).subscribe({
      next: () => {
        console.log('Affectation deleted');
        this.data.annees[anneeIndex]
          .departements[departementIndex].formations[formationIndex]
          .niveaux[niveauIndex].semestres[semestreIndex]
          .modules[moduleIndex].groupes[groupeIndex]
          .affectations.splice(affectationIndex, 1);

      },
      error: (error) => {
        console.error('Error deleting affectation:', error);
      }
    });

  }
}
