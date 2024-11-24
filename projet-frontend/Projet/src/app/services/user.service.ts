import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/responsableDepartement';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
  searchUsers(username: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${username}`);
  }
   searchUsersByRole(role: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/role/${role}`);
  }
}
