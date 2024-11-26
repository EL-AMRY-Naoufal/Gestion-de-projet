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
  public passwordIsStrong: boolean;
  public isError: boolean;

  constructor(private resetPasswordService: ResetPasswordService) {
    this.passwordChanged = false;
    this.passwordIsCorrect = true;
    this.passwordIsStrong = true;
    this.emailIsCorrect = true;
    this.isError = false;
  }

  @ViewChild('resetForm') public resetForm!: NgForm;

  resetPassword() {
    this.resetPasswordService.getUserIdByEmail(this.resetForm.value.email).pipe(
      catchError(() => {
        this.isError = true;
        return "";
      }))
    .subscribe(response => {
      if(response != null) {
        this.emailIsCorrect = true;
        if(this.isPasswordCorrect(this.resetForm.value.password, this.resetForm.value.confirmedPassword)) {
            this.resetPasswordService.resetPassword(response, this.resetForm.value.password)
            .pipe(
              catchError(error => {
                return throwError(() => new Error(error));
              }))
            .subscribe(
              response => {this.passwordChanged = true;}
            );
        }
      }
      else {  
        this.emailIsCorrect = false;
      }
    });
  }

  navigateToLogin() {
    this.resetPasswordService.navigateToLogin();
  }

  isPasswordCorrect(password: string, confirmedPassword: string) : boolean {
    if(!this.resetPasswordService.isPasswordStrong(password)) {
      this.passwordIsStrong = false;
      return false;
    }
    else {
      this.passwordIsStrong = true;
    }

    var result = this.resetPasswordService.isPasswordCorrect(password, confirmedPassword);
    console.log(password + " " + confirmedPassword);
    if(!result) {
      this.passwordIsCorrect = false;
    }
    else {
      this.passwordIsCorrect = true;
    }
    return result;
  }
}
