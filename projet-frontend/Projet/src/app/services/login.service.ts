import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' 
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/api/users/authenticate'; 

  public userRole='';
  constructor(private http: HttpClient, private router: Router) {}

  
  login(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  handleLoginSuccess(response: any) {
    this.userRole = response.user.roles[0];

    if (typeof window !== 'undefined') { 
      localStorage.setItem('userRole', response.user.roles[0]);
    }

    console.log('user', this.userRole)
    this.router.navigate(['/dashboard']);
  }

  handleLoginError(error: any) {
    console.error('Login failed:', error);
    alert('Login failed: Invalid email or password');
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
