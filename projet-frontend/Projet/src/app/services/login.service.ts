import { User } from './../componenets/shared/types/user.type';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/api/users/authenticate';
    // private property to store default person
  private _connectUser: User | undefined;

  constructor(private http: HttpClient, private router: Router) {}


  login(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  handleLoginSuccess(response: any) {
    this._connectUser = response.user;

    if (response.user.role == "CHEF_DE_DEPARTEMENT")
    {
      this.router.navigate(['/dashboard']);
    } else {
      this.router.navigate(['']);
    }
  }

  handleLoginError(error: any) {
    console.error('Login failed:', error);
    alert('Login failed: Invalid email or password');
  }

  /*
  * Returns private property _connectUser
  */
 get connectUser(): User | undefined {
    return this._connectUser;
 }


}
