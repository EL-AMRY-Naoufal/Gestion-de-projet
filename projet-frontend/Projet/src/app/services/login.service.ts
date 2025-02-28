import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { YearService } from './year-service';
import { ApiService } from './api-service';

type UserRole = {
  role: string;
  year: number;
};

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private readonly isBrowser!: boolean;

  // Is a internal state of the authentification (true if connected else false)
  private isLoggedState: boolean = true;

  // Observable for another component who depends of the auth status
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object,
    private _yearService: YearService,
    private _apiService: ApiService
  ) {

    console.log("test render");
    this.isBrowser = isPlatformBrowser(this.platformId);

    // Test if user is authenticated on app reload
    /*this._apiService.me().subscribe(
      {
        complete: () => {
          this.isAuthenticated = true;
        },
        error: () => {
          this.isAuthenticated = false;
        }
      }
    );*/
  }

  login(formData: any): Observable<any> {
    return this._apiService.login(formData);
  }

  handleLoginSuccess(response: any) {
    if (
      !response.user ||
      !response.currentYearId ||
      isNaN(response.currentYearId)
    ) {
      console.error('Incorrect login response:', response);
      this.router.navigate(['/login']);
      return;
    }

    // Reformatage des rôles
    const userRoles = response.user.roles.map((ur: any) => ({
      role: ur.role,
      year: ur.year,
    }));
    const currentYearId = response.currentYearId;

    // L'enregistrement de l'id de l'utilisateur connecté dans un local storage
    if (this.isBrowser) {
      localStorage.setItem('userId', response.user.id);

      this._yearService.currentYearId = currentYearId;

      localStorage.setItem('userRoles', JSON.stringify(userRoles));

      this.isAuthenticatedSubject.next(true);
    }

    this.router.navigate(['/enseignants/affectations']);
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
    if (!this.isBrowser) return 0; // Server environment

    return parseInt(localStorage.getItem('userId') || '0');
  }

  isLoggedIn(): boolean {
    if (!this.isBrowser) return false; // Server environment

    return this.isLoggedState;
  }

  logout() {
    this._apiService.logout().subscribe(() => {
      if (this.isBrowser) {
        localStorage.removeItem('userRoles');
        localStorage.removeItem('currentYearId');
        this.isAuthenticated = false;
      }
      this.router.navigate(['/login']);
    });
  }

  /**
   * @returns array of user roles for the current year
   */
  get userRoles(): string[] {
    if (this.isBrowser) {
      return (
        JSON.parse(localStorage.getItem('userRoles') || '[]') as UserRole[]
      )
        .filter((ur: any) => ur.year === this.currentYearId)
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

  set isAuthenticated(isAuthenticated: boolean) {
    this.isLoggedState = isAuthenticated;
    this.isAuthenticatedSubject.next(this.isLoggedState);
  }
}
