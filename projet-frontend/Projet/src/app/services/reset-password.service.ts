import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { response } from 'express';

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

  getUserIdByEmail(email: String): Observable<any> {
    return this.http.get(this.apiUrl + "/user/" + email);
  }
}
