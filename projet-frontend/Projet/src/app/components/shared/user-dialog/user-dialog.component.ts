import { Component, Inject, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { UserFormComponent, UserFormDto } from "../user-form/user-form.component"; 
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-user-dialog',
  standalone: true,
  imports: [UserFormComponent, MatDialogModule, CommonModule],
  templateUrl: './user-dialog.component.html',
  styleUrl: './user-dialog.component.scss'
})
export class UserDialogComponent {

    /**
   * Component constructor
   */
    constructor(
      private _dialogRef: MatDialogRef<UserDialogComponent, UserFormDto>,
      @Optional() @Inject(MAT_DIALOG_DATA) private _user: UserFormDto
    ) {}
    
    /**
     * Returns user passed in dialog open
     */
    get user(): UserFormDto {
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
    onSave(user: UserFormDto): void {
      this._dialogRef.close(user);
    }

}
