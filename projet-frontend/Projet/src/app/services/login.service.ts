import { User } from './../componenets/shared/types/user.type';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/api/users/authenticate';
    // private property to store default person
  public _connectUser: number = 1;

  constructor(private http: HttpClient, private router: Router) {}


  login(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  handleLoginSuccess(response: any) {

    this._connectUser = response.user.id;
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
 get connectUser(): number{
    return this._connectUser;
 }


}
