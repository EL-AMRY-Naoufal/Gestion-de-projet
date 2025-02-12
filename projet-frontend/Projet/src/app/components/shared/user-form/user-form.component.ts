import { Component, EventEmitter, Inject, Input, Output } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Roles, User, UserRoleDto } from '../types/user.type';
import { UserCustomValidators } from './user-custom-validators';
import { MAT_DIALOG_DATA, MatDialogActions } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { CommonModule, NgIf } from '@angular/common';
import { CategorieEnseignant, EnseignantDto } from '../types/enseignant.type';
import { EnseignantService } from '../../../services/enseignant.service';
import { CategorieEnseignantService } from '../../../services/categorie-enseignant.service';
import { LoginService } from '../../../services/login.service';
import { MatIconModule } from '@angular/material/icon';
import { YearService } from '../../../services/year-service';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatSelectModule, MatOptionModule, ReactiveFormsModule, MatDialogActions, MatDialogModule, NgIf, CommonModule, FormsModule, MatIconModule,
  ],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent {
  // private property to store update mode flag
  private _isUpdateMode: boolean;
  // private property to store model value
  private _model: User;
  // private property to store cancel$ value
  private readonly _cancel$: EventEmitter<void>;
  // private property to store submit$ value
  private readonly _submit$: EventEmitter<User>;
  // private property to store form value
  private readonly _form: FormGroup;

  /**
   * Component constructor
   */
  constructor(@Inject(MAT_DIALOG_DATA) public data: User,
    @Inject(MAT_DIALOG_DATA) public dataTeacher: any,
    private enseignantService: EnseignantService,
    private categorieService: CategorieEnseignantService,
    private _yearService: YearService,) {

    this._model = {} as User;
    this._isUpdateMode = !!data;
    this._submit$ = new EventEmitter<User>();
    this._cancel$ = new EventEmitter<void>();
    this._form = this._buildForm();
  }

  /**
   * Sets private property _model
   */
  @Input()
  set model(model: User) {
    this._model = model;
  }

  /**
   * Returns private property _model
   */
  get model(): User {
    return this._model;
  }

  /**
   * Returns private property _form
   */
  get form(): FormGroup {
    return this._form;
  }

  /**
   * Returns private property _isUpdateMode
   */
  get isUpdateMode(): boolean {
    return this._isUpdateMode;
  }

  /**
   * Returns private property _cancel$
   */
  @Output('cancel')
  get cancel$(): EventEmitter<void> {
    return this._cancel$;
  }

  /**
   * Returns private property _submit$
   */
  @Output('submit')
  get submit$(): EventEmitter<User> {
    return this._submit$;
  }

  /**
   * OnInit implementation
   */
  ngOnInit(): void {
    // Récupération des catégories depuis le service
    this.categorieService.getCategories().subscribe(data => {
      this.categories = data;
    });

    // Vérifier si nous sommes en mode mise à jour
    if (this._isUpdateMode && this.model.roles.some(role => role.role === 'ENSEIGNANT')) {
      // Si l'utilisateur a le rôle 'ENSEIGNANT', récupérer ses détails
      this.fetchEnseignantDetails(this._model.id!);
    }

    console.log('liste des roles', this._model.roles);
    console.log('liste des roles', this._model.roles.filter(role => role.year === this._yearService.currentYearId).map(role => role.role));
    
    this._form.patchValue({ roles: this._model
      .roles
      .filter(role => role.year === this._yearService.currentYearId)
      .map(role => role.role) 
    });

     // Ajout de la logique pour surveiller les changements de 'firstname' et 'name'
    this._form.get('firstname')?.valueChanges.subscribe(firstname => this.updateEmailAndUsername());
    this._form.get('name')?.valueChanges.subscribe(name => this.updateEmailAndUsername());

    // Ajout de la logique pour surveiller les changements de 'nbHeureCategorie '
    this._form.get('categorieEnseignant')?.valueChanges.subscribe(categorieEnseignant => this.updateNbHeureCategorie())
  }


  /**
 * Function to capitalize the first letter and keep the rest in lowercase
 */
private capitalizeFirstLetter(value: string): string {
  if (!value) return '';
  return value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
}




/**
 * Normalise une chaîne de caractères :
 * - Remplace les caractères accentués par leurs équivalents non accentués
 * - Convertit en minuscules
 * - Remplace tous les types d'espaces (y compris insécables) par des tirets
 *
 * @param value La chaîne à normaliser
 * @returns La chaîne normalisée
 */
private normalizeString(value: string): string {
  return value
    .toLowerCase() // Convertir en minuscules
    .normalize("NFD") // Décomposer les caractères accentués
    .trim(); // Supprimer les tirets en début et en fin
}



  /**
 * Met à jour automatiquement l'email et le pseudo d'utilisateur.
 */
private updateEmailAndUsername(): void {
  const firstname = this._form.get('firstname')?.value?.trim().toLowerCase() || '';
  const name = this._form.get('name')?.value?.trim().toLowerCase() || '';

  const normalizedFirstname = this.normalizeString(firstname);
  const normalizedName = this.normalizeString(name);


  if (firstname && name) {
    const formattedEmail = `${firstname}.${name}@etu.univ-lorraine.fr`;
    const formattedUsername = `${name}1u`;

    // Mise à jour des champs email et username
    this._form.get('email')?.setValue(formattedEmail, { emitEvent: false });
    this._form.get('username')?.setValue(formattedUsername, { emitEvent: false });
  }

    // Update firstname and name with the capitalized versions
    if (firstname) {
      this._form.get('firstname')?.setValue(this.capitalizeFirstLetter(firstname), { emitEvent: false });
    }
    if (name) {
      this._form.get('name')?.setValue(this.capitalizeFirstLetter(name), { emitEvent: false });
    }
}


private updateNbHeureCategorie(): void {
  const categorie = this._form.get('categorieEnseignant')?.value;

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
  this._form.get('nbHeureCategorie')?.setValue(nbHeureCategorie, { emitEvent: false });
}



  private fetchEnseignantDetails(userId: number): void {
    this.enseignantService.getEnseignantByUserId(userId).subscribe({
      next: (enseignant) => {
        this.enseignant = enseignant;
        this._form.patchValue({
          maxHeuresService: enseignant.maxHeuresService,
          categorieEnseignant: enseignant.categorieEnseignant,
          nbHeureCategorie: enseignant.nbHeureCategorie,
        });
      },
      error: (err) => {
        console.error('Failed to fetch enseignant details:', err);
      },
    });
  }

/**
 * Function to handle component update
 */
ngOnChanges(record: any): void {
  if (record.model && record.model.currentValue) {
    this._model = record.model.currentValue;
    this._isUpdateMode = true;
  } else {
    this._model = {
      id: 123,
      username: '',
      name:'',
      firstname: '',
      email: '',
      roles: [],
      password: ''
    };
    this._isUpdateMode = false;
  }

  // Vérification et traitement du rôle ENSEIGNANT
  if (this._isUpdateMode && this.form.get('roles')?.value.includes('ENSEIGNANT')) {
    this.fetchEnseignantDetails(this._model.id!);

    // Mise à jour des champs spécifiques pour un enseignant
    this._form.patchValue({
      categorieEnseignant: this.enseignant?.categorieEnseignant,
      nbHeureCategorie: this.enseignant?.nbHeureCategorie,
    });
  }

  // Mise à jour des valeurs du formulaire avec le modèle complet
  this._form.patchValue(this._model);
}

/**
 * Function to emit event to cancel process
 */
cancel(): void {
  this._cancel$.emit();
}

/**
 * Function to emit event to submit form and person
 */
submit(user: User): void {
  // Émettre l'utilisateur via l'événement _submit$
  this._submit$.emit(user);

  // Vérifier si nous sommes en mode mise à jour et si le rôle ENSEIGNANT est présent
  if (this._isUpdateMode && this.form.get('roles')?.value.includes('ENSEIGNANT')) {
    // Mise à jour des propriétés de l'enseignant avec les valeurs de l'utilisateur
    this.enseignant.categorieEnseignant = user.categorieEnseignant as CategorieEnseignant;
    this.enseignant.nbHeureCategorie = user.nbHeureCategorie as number;
    this.enseignant.maxHeuresService = user.maxHeuresService as number;
    this.enseignant.user = user;

    const userToSend: User = {
          ...user,
          roles: user.roles.map((role) => { 
            return {year: this._yearService.currentYearId, role: role as unknown as Roles}
          }),
        }

    this.enseignant.user = userToSend;
    // Appeler le service pour mettre à jour l'enseignant
    this.enseignantService.updateEnseignant(this.enseignant).subscribe(
      () => {
        console.log('Enseignant updated successfully');
      },
      error => {
        console.error('Error updating enseignant:', error);
      }
    );
  }
}

/**
 * Function to build our form
 */
private _buildForm(): FormGroup {
  const _formGroup = new FormGroup<{ [key: string]: AbstractControl<any, any> }>({
    id: new FormControl(),
    username: new FormControl(
      '',
      Validators.compose([Validators.required, Validators.minLength(2)])
    ),
    name: new FormControl(
      '',
      Validators.compose([Validators.required, Validators.minLength(2)])
    ),
    firstname: new FormControl(
      '',
      Validators.compose([Validators.required, Validators.minLength(2)])
    ),
    email: new FormControl(
      '',
      Validators.compose([Validators.required, UserCustomValidators.googleEmail])
    ),
    roles: new FormControl([], Validators.required),
    password: new FormControl(
      '',
      this._isUpdateMode
        ? null
        : Validators.compose([
            Validators.required,
            Validators.minLength(6),
            UserCustomValidators.strongPassword,
          ])
    ),
    confirmPassword: new FormControl(
      '',
      this._isUpdateMode
        ? null
        : Validators.compose([Validators.required, Validators.minLength(6)])
    ),
  });

  // Appliquer la validation personnalisée si ce n'est pas un mode de mise à jour
  if (!this._isUpdateMode) {
    _formGroup.setValidators(UserCustomValidators.matchPasswords);
  }

  // Fonction utilitaire pour ajouter des contrôles dynamiques
  const addControl = (
    name: string,
    value: any,
    baseValidators: any[] = [],
    ...extraValidators: any[]
  ) => {
    const allValidators = [...baseValidators, ...extraValidators];
    const control = new FormControl(value, Validators.compose(allValidators));
    _formGroup.addControl(name, control);
  };

  // Vérification si le rôle 'ENSEIGNANT' est présent
  const isEnseignant = this.model?.roles?.some(role => role.role == 'ENSEIGNANT')

  // Ajouter des champs spécifiques à 'ENSEIGNANT' si nécessaire
  addControl(
    'maxHeuresService',
    this.enseignant?.maxHeuresService || this.defaultHeures,
    isEnseignant ? [Validators.required] : [],
    Validators.min(0)
  );
  addControl(
    'categorieEnseignant',
    this.enseignant?.categorieEnseignant || '',
    isEnseignant ? [Validators.required] : []
  );
  addControl(
    'nbHeureCategorie',
    this.enseignant?.nbHeureCategorie || 0,
    isEnseignant ? [Validators.required] : [],
    Validators.min(0)
  );

  return _formGroup;
}



  /**
   * Adds a new control or replaces an existing one in the FormGroup.
   *
   * @param formGroup The FormGroup to modify.
   * @param controlName The name of the control to add or replace.
   * @param control The new FormControl instance to set.
   */
  private _setOrReplaceControl(
    formGroup: FormGroup,
    controlName: string,
    control: FormControl
  ): void {
    if (formGroup.contains(controlName)) {
      formGroup.setControl(controlName, control); // Replace existing control
    } else {
      formGroup.addControl(controlName, control); // Add new control
    }
  }



  defaultHeures = 192;
  categories: string[] = [];
  enseignant: EnseignantDto = {
    name: '',
    firstname: '',
    hasAccount: true,
    maxHeuresService: 192,
    categorieEnseignant: CategorieEnseignant.ATER,
    heuresAssignees: 0,
    nbHeureCategorie: 0
  };

  categoriesEnseignant = Object.values(CategorieEnseignant);


}
