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

  public userRole='';
  constructor(private http: HttpClient, private router: Router) {}


  login(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  handleLoginSuccess(response: any) {
/*
    this._connectUser = response.user.id;
    if (response.user.role == "CHEF_DE_DEPARTEMENT")
    {
      this.router.navigate(['/dashboard']);
    } else {
      this.router.navigate(['']);
    */

      
    this.userRole = response.user.role;

    if (typeof window !== 'undefined') {
      localStorage.setItem('userRole', response.user.role);
    }

    console.log('user', this.userRole)
    this.router.navigate(['/dashboard']);
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


  isLoggedIn(): boolean {
     if (typeof window === 'undefined') return false; // Server environment

    return !!localStorage.getItem('userRole');
  }

  logout() {
     if (typeof window !== 'undefined') {
      localStorage.removeItem('userRole');
    }
    this.userRole = '';
    this.router.navigate(['/login']);
  }

  getUserRole(): string {
    if (typeof window !== 'undefined' && localStorage.getItem('userRole')) {
      return localStorage.getItem('userRole') || '';
    }
    return '';
  }

}
