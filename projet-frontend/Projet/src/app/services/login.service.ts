import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment.prod';
import { isPlatformBrowser } from '@angular/common';
import { YearService } from './year-service';

type UserRole = {
  role: string;
  year: number;
};

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private readonly _backendURL: any;

  private readonly isBrowser!: boolean;

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient, private router: Router, @Inject(PLATFORM_ID) private platformId: Object, private _yearService: YearService) {
    this._backendURL = {};

    this.isBrowser = isPlatformBrowser(this.platformId);

    this.isAuthenticatedSubject.next(this.isLoggedIn());

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
      year: ur.year,
    }));
    const authToken = response.token;
    const currentYearId = response.currentYearId;

    //l'enregistrement de l'id de l'utilisateur connecté dans un local storage
    if (this.isBrowser) {
      localStorage.setItem('userId', response.user.id);

      this._yearService.currentYearId = currentYearId;

      localStorage.setItem('userRoles', JSON.stringify(userRoles));
      if(authToken) localStorage.setItem('token', authToken);

      this.isAuthenticatedSubject.next(true);
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
      this.isAuthenticatedSubject.next(false);
    }
    this.router.navigate(['/login']);
  }

  /**
   * @returns array of user roles for the current year
   */
  get userRoles(): string[] {
    if (this.isBrowser) {
      return (JSON.parse(localStorage.getItem('userRoles') || '[]') as UserRole[])
        .filter((ur: any) => ur.year=== this.currentYearId)
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
