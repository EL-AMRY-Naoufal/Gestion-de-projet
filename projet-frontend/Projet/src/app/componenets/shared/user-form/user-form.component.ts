import { Component, EventEmitter, Inject, Input, Output } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../types/user.type';
import { UserCustomValidators } from './user-custom-validators';
import { MAT_DIALOG_DATA, MatDialogActions } from '@angular/material/dialog';
import { MatDialogModule } from '@angular/material/dialog';
import { CommonModule, NgIf } from '@angular/common';
import { CategorieEnseignant, EnseignantDto } from '../../../types/enseignant.type';
import { EnseignantService } from '../../../services/enseignant.service';
import { CategorieEnseignantService } from '../../../services/categorie-enseignant.service';
import { MatIconModule } from '@angular/material/icon';

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
    private categorieService: CategorieEnseignantService
  ) {
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
    this.categorieService.getCategories().subscribe(data => {
      this.categories = data;
    });
    if (this._isUpdateMode && this._model.roles.includes('ENSEIGNANT')) {
      this.fetchEnseignantDetails(this._model.id!);
    }
  }

  private fetchEnseignantDetails(userId: number): void {
    this.enseignantService.getEnseignant(userId).subscribe({
      next: (enseignant) => {
        this.enseignant = enseignant;
        this._form.patchValue({
          roles: this._model.roles,
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
        email: '',
        roles: [],
        password: ''
      };
      this._isUpdateMode = false;
    }
    if (this._isUpdateMode && this._model.roles.includes('ENSEIGNANT')) {
      this.fetchEnseignantDetails(this._model.id!);
      this._form.patchValue({
        categorieEnseignant: this.enseignant.categorieEnseignant,
        nbHeureCategorie: this.enseignant.nbHeureCategorie,
      });
    }
    

    // update form's values with model
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
    this._submit$.emit(user);
    if (this._isUpdateMode && this.model.roles.includes('ENSEIGNANT')) {
      this.enseignant.categorieEnseignant = user.categorieEnseignant as CategorieEnseignant;
      this.enseignant.nbHeureCategorie = user.nbHeureCategorie as number;
      this.enseignant.maxHeuresService = user.maxHeuresService as number;
      this.enseignantService.updateEnseignant(this.enseignant).subscribe(

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
      email: new FormControl(
        '',
        Validators.compose([Validators.required, UserCustomValidators.googleEmail])
      ),
      roles: new FormControl('', Validators.required),
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
  
    if (!this._isUpdateMode) {
      _formGroup.setValidators(UserCustomValidators.matchPasswords);
    }
  
    const addControl = (
      name: string,
      value: any,
      baseValidators: any[] = [],
      ...extraValidators: any[]
    ) => {
      const allValidators = [...baseValidators, ...extraValidators]; // Combine les validateurs
      const control = new FormControl(value, Validators.compose(allValidators));
      _formGroup.addControl(name, control);
    };
    
  
    const isEnseignant = this.model?.roles?.includes('ENSEIGNANT');
    addControl(
      'maxHeuresService',
      this.enseignant?.maxHeuresService || this.defaultHeures,
      isEnseignant ? [Validators.required] : [],
      Validators.min(0),
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
      Validators.min(0),
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
    maxHeuresService: 192,
    categorieEnseignant: CategorieEnseignant.PROFESSEUR,
    heuresAssignees: 0,
    nbHeureCategorie: 0
  };

  categoriesEnseignant = Object.values(CategorieEnseignant);

}
