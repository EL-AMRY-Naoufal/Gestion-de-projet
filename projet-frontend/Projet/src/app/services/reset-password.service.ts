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
    console.log("password reset in service");
    return this.http.put(this.apiUrl + "/" + userId + "/" + this.apiUrlKeyword, password);
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']);
  }

  /* DOES NOT WORK THIS WAY BECAUSE OF ASYNC */
  // isEntryCorrect(form: any): boolean {
  //   return this.isPasswordCorrect(form.value.password, form.value.passwordConfirmation)
  //          && this.userEmailExists(form.value.email);
  // }

  isPasswordCorrect(password: string, confirmedPassword: string) : boolean {
    return password == confirmedPassword;
  }

  getUserIdByEmail(email: String): Observable<any> {
    return this.http.get(this.apiUrl + "/user/" + email);
  }

  /* DOES NOT WORK THIS WAY BECAUSE OF ASYNC */
  // userEmailExists(email: string): boolean {
  //   var received : boolean = false;
  //   var userExists: boolean = false;
  //   this.getUserIdByEmail(email).subscribe(response => {userExists = (response != null); received = true; console.log("result : " +  response); return userExists;});
  //   console.log(userExists);
  //   return userExists;
  // }
}
