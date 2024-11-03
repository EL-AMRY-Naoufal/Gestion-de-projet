import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from '../types/user.types';
import { EnseignantDto } from '../types/enseignant.type';

@Injectable({
  providedIn: 'root'
})
export class UpdateProfesseurService {

  private apiUrl = `${environment.apiUrl}/enseigants`;

  constructor(private http: HttpClient) { }

  getEnseignantsNotInEnseignantTable(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/enseignants-non-enregistres`);
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
