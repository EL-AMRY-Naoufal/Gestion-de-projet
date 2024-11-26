import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { response } from 'express';
import { AbstractControl, ValidationErrors } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService {
  private apiUrl = 'http://localhost:8080/api/users';
  private apiUrlKeyword = 'password';

  constructor(private http: HttpClient, private router: Router) {}

  resetPassword(userId: string, password: String): Observable<any> {
    return this.http.put(this.apiUrl + "/" + userId + "/" + this.apiUrlKeyword, password);
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  isPasswordCorrect(password: string, confirmedPassword: string) : boolean {
    return password == confirmedPassword;
  }

  isPasswordStrong(password: string) : boolean {
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    return passwordPattern.test(password);
  }

  getUserIdByEmail(email: String): Observable<any> {
    return this.http.get(this.apiUrl + "/user/" + email);
  }

  /**
   * Custom validator to ensure that the email format is correct
   * (e.g., name.lastname@gmail.com)
   */
  static googleEmail(control: AbstractControl): ValidationErrors | null {
      const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return emailPattern.test(control.value) ? null : { googleEmail: true };
  }
}
