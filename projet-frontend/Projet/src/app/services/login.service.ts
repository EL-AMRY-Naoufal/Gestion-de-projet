import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' 
})
export class LoginService {
  private apiUrl = 'http://localhost:8080/api/users/authenticate'; 

  constructor(private http: HttpClient, private router: Router) {}

  
  login(formData: any): Observable<any> {
    console.log( formData)
    return this.http.post(this.apiUrl, formData);
  }

  handleLoginSuccess(response: any) {
    console.log('Login successful:', response);
    this.router.navigate(['/dashboard']);
  }

  handleLoginError(error: any) {
    console.error('Login failed:', error);
    alert('Login failed: Invalid email or password');
  }
}
