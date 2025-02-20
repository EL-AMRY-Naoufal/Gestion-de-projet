import { Roles } from './../shared/types/user.type';
import { CommonModule } from '@angular/common';
import { Component, Inject, ChangeDetectorRef, ViewEncapsulation } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { CategorieEnseignantService } from '../../services/categorie-enseignant.service';
import { UserService } from '../../services/user.service';
import { CategorieEnseignant, EnseignantDto } from '../shared/types/enseignant.type';
import { EnseignantService } from '../../services/enseignant.service';
import { User } from '../shared/types/user.type';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-professeur',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatCheckboxModule,
    ReactiveFormsModule,
    MatDialogModule, // ✅ Ajout du module MatDialog

  ],
  providers: [EnseignantService],
  templateUrl: './update-professeur.component.html',
  styleUrl: './update-professeur.component.scss',
  encapsulation: ViewEncapsulation.None, // Désactive l'encapsulation pour appliquer les styles globaux

})
export class UpdateProfesseurComponent {
  defaultHeures = 192;
  categories: string[] = [];
  categoriesEnseignant = Object.values(CategorieEnseignant);
  userFullName: string = '';
  utilisateurs: User[] = [];

  isEdit = false;
  isEditandUserNull = false;
  form: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<UpdateProfesseurComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EnseignantDto,
    private enseignantService: EnseignantService,
    private categorieService: CategorieEnseignantService,
    private fb: FormBuilder,
  ) {

    // Initialisation du formulaire
    this.form = this.fb.group({
      id: [data?.id || null],
      hasAccount: [data?.hasAccount || false],
      name: [data?.name || '', Validators.required],
      firstname: [data?.firstname || '', Validators.required],
      user: [data?.user || null],
      maxHeuresService: [data?.maxHeuresService || this.defaultHeures , [Validators.required, Validators.min(0)]],
      categorieEnseignant: [data?.categorieEnseignant || null, Validators.required],
      nbHeureCategorie: [data?.nbHeureCategorie || 0, [Validators.required, Validators.min(0)]],
    });
  this.setupConditionalValidation();

    if (data) {
      this.isEdit = true;
      if(data.user){
        this.userFullName = `${data.user.firstname} ${data.user.name}`;
      }
      else{
        this.isEditandUserNull = true;
      }
    }
  }

  setupConditionalValidation() {
    const hasAccountControl = this.form.get('hasAccount');
    const nameControl = this.form.get('name');
    const firstnameControl = this.form.get('firstname');
    const userControl = this.form.get('user');

    // Appliquer les validations au chargement initial
    this.toggleValidators(hasAccountControl?.value);

    // Écoute des changements sur hasAccount
    hasAccountControl?.valueChanges.subscribe((hasAccount) => {
      this.toggleValidators(hasAccount);
    });
  }

  private updateNbHeureCategorie(): void {
    const categorie = this.form.get('categorieEnseignant')?.value;

    let nbHeureCategorie = 0;

    // Définir le nombre d'heures selon la catégorie
    switch (categorie) {
      case 'ENSEIGNANT_CHERCHEUR':
        nbHeureCategorie = 192;
        break;
      case 'PRAG':
        nbHeureCategorie = 384;
        break;
      case 'ATER':
        nbHeureCategorie = 192;
        break;
      case 'DCCE':
        nbHeureCategorie = 64;
        break;
      case 'VACATAIRE':
        nbHeureCategorie = 32;
        break;
      default:
        console.warn(`Catégorie non reconnue : ${categorie}`);
        break;
    }

    // Mettre à jour le champ 'nbHeureCategorie' avec la valeur correspondante
    this.form.get('nbHeureCategorie')?.setValue(nbHeureCategorie, { emitEvent: false });
  }

  toggleValidators(hasAccount: boolean) {
    const nameControl = this.form.get('name');
    const firstnameControl = this.form.get('firstname');
    const userControl = this.form.get('user');

    if (hasAccount) {
      // Si l'utilisateur a un compte, "name" et "firstname" ne sont pas requis, mais "user" l'est
      nameControl?.clearValidators();
      firstnameControl?.clearValidators();
      userControl?.setValidators([Validators.required]);
    } else {
      // Sinon, "name" et "firstname" sont requis, et "user" ne l'est pas
      nameControl?.setValidators([Validators.required, Validators.minLength(2)]);
      firstnameControl?.setValidators([Validators.required, Validators.minLength(2)]);
      userControl?.clearValidators();
    }

    // Mise à jour des validations
    nameControl?.updateValueAndValidity();
    firstnameControl?.updateValueAndValidity();
    userControl?.updateValueAndValidity();
  }

  populateForm(enseignant: EnseignantDto) {
    this.form.patchValue({
      hasAccount: enseignant.hasAccount,
      name: enseignant.name,
      firstname: enseignant.firstname,
      user: enseignant.user,
      maxHeuresService: enseignant.maxHeuresService,
      categorieEnseignant: enseignant.categorieEnseignant,
      nbHeureCategorie: enseignant.nbHeureCategorie,
    });
  }


  ngOnInit(): void {
    this.categorieService.getCategories().subscribe(data => {
      this.categories = data;
    });
    !this.isEdit && this.enseignantService.getEnseignantsNotInEnseignantTable().subscribe(data => {
      this.utilisateurs = data;
    });

    this.isEditandUserNull && this.enseignantService.getUserWithSameEnseignantNameAndFirstName(this.data).subscribe(data => {
      this.utilisateurs = data;
    });
    this.form.get('categorieEnseignant')?.valueChanges.subscribe(_categorieEnseignant => this.updateNbHeureCategorie())
  }


save() {

  if (this.form.invalid) {
    this.form.markAllAsTouched();  // Marque tous les champs comme "touchés" pour afficher les erreurs
    return; // Empêche la soumission si le formulaire est invalide
  }


    console.log('Form:', this.form);
    if (this.form.valid) {
      const enseignantData = this.form.value;
      if (this.isEdit) {
        this.enseignantService.updateEnseignant(enseignantData).subscribe(
          (response) => this.dialogRef.close(enseignantData),
          (error) => console.error('Error updating enseignant:', error)
        );
      } else {
        this.enseignantService.createEnseignant(enseignantData).subscribe(
          (response) => this.dialogRef.close(enseignantData),
          (error) => console.error('Error creating enseignant:', error)
        );
      }
    }
  }


  close() {
    this.dialogRef.close();
  }
}
