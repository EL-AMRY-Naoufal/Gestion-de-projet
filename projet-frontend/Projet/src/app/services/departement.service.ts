import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.prod";
import { DepartementType } from "../componenets/shared/types/departement.type";
import { catchError, map, tap } from 'rxjs/operators';
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

  getDepartementsByYear(yearId: number): Observable<DepartementType[]> {
    return this.http.get<DepartementType[]>(`${this._backendURL.departements}/year/${yearId}`);
  }
}
