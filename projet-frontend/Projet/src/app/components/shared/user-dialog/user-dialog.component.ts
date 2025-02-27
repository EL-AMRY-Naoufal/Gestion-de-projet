import { Component, Inject, Optional, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { UserFormComponent } from "../user-form/user-form.component";
import { CommonModule } from '@angular/common';
import { User } from '../types/user.type';


@Component({
  selector: 'app-user-dialog',
  standalone: true,
  imports: [UserFormComponent, MatDialogModule, CommonModule],
  templateUrl: './user-dialog.component.html',
  styleUrl: './user-dialog.component.scss',
  encapsulation: ViewEncapsulation.None, // Désactive l'encapsulation pour appliquer les styles globaux

})
export class UserDialogComponent {

    /**
   * Component constructor
   */
    constructor(
      private _dialogRef: MatDialogRef<UserDialogComponent, User>,
      @Optional() @Inject(MAT_DIALOG_DATA) private _user: User
    ) {}

    /**
     * Returns user passed in dialog open
     */
    get user(): User {
      return this._user;
    }

    /**
     * OnInit implementation
     */
    ngOnInit(): void {}

    /**
     * Function to cancel the process and close the modal
     */
    onCancel(): void {
      this._dialogRef.close();
    }

    /**
     * Function to close the modal and send user to parent
     */
    onSave(user: User): void {
      this._dialogRef.close(user);
    }

}
