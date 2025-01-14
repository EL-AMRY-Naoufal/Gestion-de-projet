import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.prod";
import { DepartementType } from "../componenets/shared/types/departement.type";
import { AnneeType } from "../componenets/shared/types/annee.type";

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
    return this.http.get<AnneeType>(`${this._backendURL.annees}/${id}`)
  }
}