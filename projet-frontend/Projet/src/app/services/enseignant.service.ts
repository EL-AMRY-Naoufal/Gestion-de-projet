import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {AffectationType} from "../Type/Affectation";
import { environment } from '../../environments/environment';
import { EnseignantDto } from '../types/enseignant.type';
import { User } from '../types/user.types';

@Injectable({
  providedIn: 'root'
})
export class EnseignantService {
  private apiUrl = `${environment.apiUrl}/enseignants`;

  constructor(private http: HttpClient) { }

  getAffectationsByEnseignantId(id: number): Observable<any> {
    return this.http.get<AffectationType>(`${this.apiUrl}/${id}/affectations`);
  }

  getUserByName(name: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}?name=${name}`);
  }
  
  getEnseignantsNotInEnseignantTable(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/enseignants-non-enregistres`);
  }

  getEnseignants(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}`);
  }

  createEnseignant(enseignant: EnseignantDto): Observable<EnseignantDto> {
    return this.http.post<EnseignantDto>(`${this.apiUrl}`, enseignant);
  }
  updateEnseignant(enseignant: EnseignantDto): Observable<EnseignantDto> {
    return this.http.put<EnseignantDto>(`${this.apiUrl}`, enseignant);
  }
  getEnseignant(id: number): Observable<EnseignantDto> {
    return this.http.get<EnseignantDto>(`${this.apiUrl}/${id}`);
  }
}