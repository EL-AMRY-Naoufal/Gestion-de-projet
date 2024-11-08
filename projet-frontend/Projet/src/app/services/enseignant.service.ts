import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {AffectationType} from "../Type/Affectation";

@Injectable({
  providedIn: 'root'
})
export class EnseignantService {
  private apiUrl = 'http://localhost:8080/api/enseignants';

  constructor(private http: HttpClient) { }

  getAffectationsByEnseignantId(id: number): Observable<any> {
    return this.http.get<AffectationType>(`${this.apiUrl}/${id}/affectations`);
  }

  getUserByName(name: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}?name=${name}`);
  }
}
