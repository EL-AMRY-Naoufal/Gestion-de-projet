import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';

type UserRole = {
  role: string;
  yearId: number;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly _backendURL: any;

  public userRoles: UserRole[] = [];
  private authToken: string | null = null;
  private currentYearId: number | null = null;

  constructor(private http: HttpClient, private router: Router) {
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
    // Verify response is correct
    if(!response.user || !response.token || !response.currentYearId || isNaN(response.currentYearId)) {
      console.error("Incorrect login response:", response);
      this.router.navigate(['/login']);
      return;
    }

    this.userRoles = response.user.roles.map((ur: any) => ({ role: ur.role, yearId: ur.year }));
    this.authToken = response.token;

    //l'enregistrement de l'id de l'utilisateur connecté dans un local storage
    localStorage.setItem('userId', response.user.id);
    localStorage.setItem('currentYearId', response.currentYearId);

    if (typeof window !== 'undefined') {
      localStorage.setItem('userRoles', JSON.stringify(this.userRoles));
      if(this.authToken) localStorage.setItem('token', this.authToken);
    }

    console.log(this.getUserRoles());

    this.router.navigate(['/dashboard']);
  }

  handleLoginError(error: any) {
    console.error('Login failed:', error);
    alert('Login failed: Invalid email or password');
  }

  goToResetPasswordPage(): void {
    this.router.navigate(['/reset-password']);
  }

  /*
  * Returns private property _connectUserID
  */
  connectUser(): number{
    return parseInt(localStorage.getItem('userId') || '0');
  }

  isLoggedIn(): boolean {
     if (typeof window === 'undefined') return false; // Server environment

    return !!localStorage.getItem('userRoles') && !!localStorage.getItem('token');
  }

  logout() {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('userRoles');
      localStorage.removeItem('token');
    }
    this.userRoles = [];
    this.router.navigate(['/login']);
  }

  /**
   * @returns array of user roles for the current year
   */
  getUserRoles(): string[] {
    if (typeof window !== 'undefined' && localStorage.getItem('userRoles')) {
      const currentYearId = parseInt(localStorage.getItem('currentYearId') || '0');
      return JSON.parse(localStorage.getItem('userRoles') || "[]")
        .filter((ur: any) => ur.yearId === currentYearId).map((ur: any) => ur.role);
    }
    return [];
  }

  getCurrentYearId(): number | null {
    if (typeof window !== 'undefined' && localStorage.getItem('currentYearId')) {
      return parseInt(localStorage.getItem('currentYearId') as string);
    }
    return null;
  }

  getAuthToken(): string | null {
    if (typeof window !== 'undefined' && localStorage.getItem('token')) {
      return localStorage.getItem('token');
    }
    return null;
  }
}
