import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { isPlatformBrowser } from '@angular/common';

type UserRole = {
  role: string;
  yearId: number;
};

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private readonly _backendURL: any;

  private readonly isBrowser!: boolean;

  constructor(private http: HttpClient, private router: Router, @Inject(PLATFORM_ID) private platformId: Object) {
    this._backendURL = {};

    this.isBrowser = isPlatformBrowser(this.platformId);

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
    if (
      !response.user ||
      !response.token ||
      !response.currentYearId ||
      isNaN(response.currentYearId)
    ) {
      console.error('Incorrect login response:', response);
      this.router.navigate(['/login']);
      return;
    }

    const userRoles = response.user.roles.map((ur: any) => ({
      role: ur.role,
      yearId: ur.year,
    }));
    const authToken = response.token;
    const currentYearId = response.currentYearId;

    //l'enregistrement de l'id de l'utilisateur connecté dans un local storage
    if (this.isBrowser) {
      console.log(authToken);
      localStorage.setItem('userId', response.user.id);
      localStorage.setItem('currentYearId', currentYearId + '');
      localStorage.setItem('userRoles', JSON.stringify(userRoles));
      if(authToken) localStorage.setItem('token', authToken);
    }

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
  connectUser(): number {
    if(!this.isBrowser) return 0; // Server environment

    return parseInt(localStorage.getItem('userId') || '0');
  }

  isLoggedIn(): boolean {
    if (!this.isBrowser) return false; // Server environment

    return (
      !!localStorage.getItem('userRoles') && !!localStorage.getItem('token')
    );
  }

  logout() {
    if(this.isBrowser) {
      localStorage.removeItem('userRoles');
      localStorage.removeItem('token');
    }
    this.router.navigate(['/login']);
  }

  /**
   * @returns array of user roles for the current year
   */
  get userRoles(): string[] {
    if (this.isBrowser) {
      return (JSON.parse(localStorage.getItem('userRoles') || '[]') as UserRole[])
        .filter((ur: any) => ur.yearId === this.currentYearId)
        .map((ur: any) => ur.role);
    }
    return [];
  }

  get currentYearId(): number | null {
    if (this.isBrowser) {
      return parseInt(localStorage.getItem('currentYearId') as string);
    }
    return null;
  }

  get authToken(): string | null {
    if (this.isBrowser) {
      return localStorage.getItem('token');
    }
    return null;
  }
}
