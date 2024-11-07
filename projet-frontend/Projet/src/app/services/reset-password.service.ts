import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResetPasswordService {
  private apiUrl = 'http://localhost:8080/api/users';
  private apiUrlKeyword = 'password';

  constructor(private http: HttpClient, private router: Router) {}

  resetPassword(password: String): Observable<any> {
      console.log(password);
      return this.http.put(this.apiUrl + "/1/" + this.apiUrlKeyword, password); //TODO put user id + hash?
    }
}
