import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.prod";
import { Annee } from "../components/shared/types/modules.types";

@Injectable({
  providedIn: 'root'
})
export class AnneeService {
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

  getAllAnnees(): Observable<any> {
    return this.http.get<any>(`${this._backendURL.annees}`);
  }

  getAnneeById(id: number): Observable<any> {
    return this.http.get<Annee>(`${this._backendURL.annees}/${id}`)
  }

  saveAnnee(annee: Annee): Observable<Annee> {
    // annee.departements = [];
    return this.http.post<Annee>(`${this._backendURL.annees}`, annee);
  }
}
