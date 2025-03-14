import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',

  templateUrl: "./login.component.html",
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [FormsModule]
})
export class LoginComponent implements OnInit {

  constructor(
    private loginService: LoginService,
    private router: Router
  ) {
    loginService.isAuthenticated$.subscribe((isAuthenticated) => {
      if (isAuthenticated) {
        this.router.navigate(['/enseignants/affectations']);
      }
    });
  }

  ngOnInit(): void {
    if (this.loginService.isAuthenticated) {
      this.router.navigate(['/dashboard']);
    }
  }

  login(form: any) {
    const formData = form.value;

    this.loginService.login(formData)
      .subscribe(
        response => { this.loginService.handleLoginSuccess(response)},
        error => { this.loginService.handleLoginError(error)}
      );
  }

  goToResetPasswordPage(): void {
    this.loginService.goToResetPasswordPage();
  }
}
