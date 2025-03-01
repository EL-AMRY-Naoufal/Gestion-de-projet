import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.prod";
import { Departement, ResponsableDepartement } from "../components/shared/types/modules.types";
@Injectable({
  providedIn: 'root'
})
export class DepartementService {
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

  getDepartementsByYear(yearId: number): Observable<Departement[]> {
    return this.http.get<Departement[]>(`${this._backendURL.departements}/year/${yearId}`);
  }

  getAllDepartements(): Observable<Departement[]> {
    return this.http.get<Departement[]>(`${this._backendURL.departements}`);
  }

  getRDDById(id: number): Observable<ResponsableDepartement> {
    return this.http.get<ResponsableDepartement>(`${this._backendURL.responsableDepartement}/id/${id}`)
  }

  saveDepartement(departement: Departement): Observable<Departement> {
    return this.http.post<Departement>(`${this._backendURL.departements}`, departement);
  }

  deleteDepartement(departement: Departement): Observable<Departement> {
    return this.http.delete<Departement>(`${this._backendURL.departements}/${departement.id}`);
  }
}
