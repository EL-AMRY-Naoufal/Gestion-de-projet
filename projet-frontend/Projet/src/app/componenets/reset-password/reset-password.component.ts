import { Component } from '@angular/core';
import { ResetPasswordService } from '../../services/reset-password.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-reset-password.component',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss'
})
export class ResetPasswordComponent {
  public passwordChanged : boolean;
  constructor(private resetPasswordService: ResetPasswordService) {
      this.passwordChanged = false;
    }

    resetPassword(form: any) {
      const formData = form.value;
      console.log("passwordChanged : " + this.passwordChanged);
      this.resetPasswordService.resetPassword(formData.password)
        .subscribe(
          response => { console.log("response"); console.log(response); this.passwordChanged = true},
          error => { console.log("error"); console.log(error);}
        );
    }

    isEntryCorrect(form: any): boolean {
      const formData = form.value;
      console.log(formData);
      return formData.password == formData.passwordConfirmation;
    }
    navigateToLogin() {
      this.resetPasswordService.navigateToLogin();
    }
}
