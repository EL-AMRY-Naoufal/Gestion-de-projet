import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { EnseignantDto } from '../components/shared/types/enseignant.type';
import { User } from '../components/shared/types/user.type';

@Injectable({
  providedIn: 'root'
})
export class UpdateProfesseurService {

  private readonly _backendURL: any;

  constructor(private http: HttpClient) {
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

  getEnseignantsNotInEnseignantTable(): Observable<User[]> {
    return this.http.get<User[]>(`${this._backendURL.allEnseignants}/enseignants-non-enregistres`);
  }

  createEnseignant(enseignant: EnseignantDto): Observable<EnseignantDto> {
    return this.http.post<EnseignantDto>(`${this._backendURL.allEnseignants}`, enseignant);
  }
  updateEnseignant(enseignant: EnseignantDto): Observable<EnseignantDto> {
    return this.http.put<EnseignantDto>(`${this._backendURL.allEnseignants}`, enseignant);
  }
  getEnseignant(id: number): Observable<EnseignantDto> {
    return this.http.get<EnseignantDto>(`${this._backendURL.allEnseignants}/${id}`);
  }
}
