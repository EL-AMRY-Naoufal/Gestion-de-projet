import { AfterViewInit, ChangeDetectorRef, Component, ViewChild } from '@angular/core';
import { ResetPasswordService } from '../../services/reset-password.service';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { catchError, throwError } from 'rxjs';

@Component({
  selector: 'app-reset-password.component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent{
  public passwordChanged : boolean;
  public passwordIsCorrect: boolean;
  public emailIsCorrect: boolean;
  constructor(private resetPasswordService: ResetPasswordService) {
      this.passwordChanged = false;
      this.passwordIsCorrect = true;
      this.emailIsCorrect = true;
    }

    @ViewChild('resetForm') public resetForm!: NgForm;

    resetPassword() {
      this.resetPasswordService.getUserIdByEmail(this.resetForm.value.email).pipe(
        catchError(() => {
          this.emailIsCorrect = false;
          return "";
        }))
      .subscribe(response => {
        this.emailIsCorrect = true;
        if(this.isPasswordCorrect(this.resetForm.value.password, this.resetForm.value.confirmedPassword)) {
            this.passwordIsCorrect = true;
            this.resetPasswordService.resetPassword(response, this.resetForm.value.password)
            .pipe(
              catchError(error => {
                return throwError(() => new Error(error));
              }))
            .subscribe(
              response => {this.passwordChanged = true;}
            );          
        }
        else {
          this.passwordIsCorrect = false;
        }
      });

      /*CANT USE FUNCTIONS BASED ON OBSERVABLES BECAUSE OF ASYNC*/
      // if(this.isEntryCorrect(this.resetForm.value.password, this.resetForm.value.confirmedPassword, this.resetForm.value.email)) {
      //   this.resetPasswordService.resetPassword(this.resetForm.value.password)
      //     .subscribe(
      //       response => { console.log("response"); console.log(response); this.passwordChanged = true},
      //       error => { console.log("error"); console.log(error);}
      //   );
      // }
    }

    navigateToLogin() {
      this.resetPasswordService.navigateToLogin();
    }

    isPasswordCorrect(password: string, confirmedPassword: string) : boolean {
      var result = this.resetPasswordService.isPasswordCorrect(password, confirmedPassword);
      return result;
    }
    /* DOES NOT WORK THIS WAY BECAUSE OF ASYNC */
    // userEmailExists(email: string): boolean {
    //   return this.resetPasswordService.userEmailExists(email);
    // }

    /* DOES NOT WORK THIS WAY BECAUSE OF ASYNC */
    // isEntryCorrect(password: string, confirmedPassword: string, email: string): boolean {
    //   this.passwordIsCorrect = this.isPasswordCorrect(password, confirmedPassword);
    //   this.emailIsCorrect = this.userEmailExists(email);
    //   return this.passwordIsCorrect && this.emailIsCorrect;
    // }
}
