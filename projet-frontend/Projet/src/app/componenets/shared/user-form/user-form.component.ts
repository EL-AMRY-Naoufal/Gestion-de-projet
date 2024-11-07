import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../types/user.type';
import { UserCustomValidators } from './user-custom-validators';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [MatFormFieldModule,MatInputModule,MatSelectModule,MatOptionModule,ReactiveFormsModule],
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
  constructor() {
    this._model = {} as User;
    this._isUpdateMode = false;
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
  ngOnInit(): void {}

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
        role: 'ENSEIGNANT', // Valeur par défaut
        password: ''
      };
      this._isUpdateMode = false;
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
  }

  /**
   * Function handle isManager checkbox value change
   */
  isManagerChecked(checked: boolean): void {
    this._form.patchValue({ isManager: checked });
  }

  /**
   * Function to build our form
   */
  private _buildForm(): FormGroup {
    return new FormGroup({
      id: new FormControl(),
      username: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(2)])
      ),
      email: new FormControl(
        '',
        Validators.compose([Validators.required, UserCustomValidators.googleEmail])
      ),
      role: new FormControl(
        '', 
        Validators.required
      ),
      // Champ pour le mot de passe avec validation (minimum 6 caractères)
      password: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(6), UserCustomValidators.strongPassword])
      ),
      // Champ pour le mot de passe avec validation (minimum 6 caractères)
      confirmPassword: new FormControl(
        '',
        Validators.compose([Validators.required, Validators.minLength(6), UserCustomValidators.matchPasswords])
      ),

    });
  }
}
