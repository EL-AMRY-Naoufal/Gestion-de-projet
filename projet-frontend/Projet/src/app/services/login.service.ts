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

  public userRoles = [];
  private authToken: string | null = null;
  constructor(private http: HttpClient, private router: Router) {}


  login(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  handleLoginSuccess(response: any) {
    console.log(response.token);
    this.userRoles = response.user.roles;
    this.authToken = response.token;

    if (typeof window !== 'undefined') {
      localStorage.setItem('userRole', JSON.stringify(this.userRoles));
      if(this.authToken) localStorage.setItem('token', this.authToken);
    }

    console.log('user', this.userRoles)
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

    return !!localStorage.getItem('userRole') && !!localStorage.getItem('token');
  }

  logout() {
     if (typeof window !== 'undefined') {
      localStorage.removeItem('userRole');
      localStorage.removeItem('token');
    }
    this.userRoles = [];
    this.router.navigate(['/login']);
  }

  getUserRoles(): string[] {
    if (typeof window !== 'undefined' && localStorage.getItem('userRole')) {
      return JSON.parse(localStorage.getItem('userRole') || '[]');
    }
    return [];
  }

  getAuthToken(): string | null {
    console.log("getAuthToken");
    if (typeof window !== 'undefined' && localStorage.getItem('token')) {
      return localStorage.getItem('token');
    }
    return null;
  }

 
}
