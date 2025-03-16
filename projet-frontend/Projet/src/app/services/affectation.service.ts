import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { Affectation, CoAffectation } from "../components/shared/types/modules.types";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AffectationService {
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

  getAllAffectations(): Observable<Affectation[]> {
    return this.http.get<Affectation[]>(`${this._backendURL.affectations}`);
  }


  getCoAffectationsByModule(moduleId: number): Observable<CoAffectation[]> {
    return this.http.get<CoAffectation[]>(`${this._backendURL.allCoAffectations}/module/${moduleId}`);
  }

  saveAffectation(affectation : Affectation, anneeId: number): Observable<Affectation> {
    return this.http.post<Affectation>(`${this._backendURL.affectations}/${anneeId}`, affectation);
  }
}
