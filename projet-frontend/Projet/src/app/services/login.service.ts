import { User } from './../componenets/shared/types/user.type';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly _backendURL: any;

  public userRoles = [];
  private authToken: string | null = null;
  constructor(private http: HttpClient, private router: Router  ) {
    this._backendURL = {};


     // build backend base url
     let baseUrl = `${environment.backend.protocol}://${environment.backend.host}`;
     if (environment.backend.port) {
       baseUrl += `:${environment.backend.port}`;
     }

     // build all backend urls
     // @ts-ignore
     Object.keys(environment.backend.endpoints).forEach(
       (k) =>
         // @ts-ignore
         (this._backendURL[k] = `${baseUrl}${environment.backend.endpoints[k]}`)
     );

  }


  login(formData: any): Observable<any> {
    return this.http.post(this._backendURL.authenticate, formData);
  }

  handleLoginSuccess(response: any) {
    this.userRoles = response.user.roles;
    this.authToken = response.token;

    //l'enregistrement de l'id de l'utilisateur connecté dans un local storage
    localStorage.setItem('userId', response.user.id);

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
  * Returns private property _connectUserID
  */
  connectUser(): number{
    return parseInt(localStorage.getItem('userId') || '0');
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
