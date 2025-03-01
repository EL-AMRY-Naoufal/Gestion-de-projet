import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment.prod";
import { Observable } from "rxjs";
import { Formation } from "../components/shared/types/modules.types";

@Injectable({
  providedIn: 'root'
})

export class FormationService {
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

    getAllFormations(): Observable<Formation[]> {
        return this.http.get<Formation[]>(`${this._backendURL.formations}`);
    }

    saveFormation(formation : Formation): Observable<Formation> {
        return this.http.post<Formation>(`${this._backendURL.formations}`, formation);
    }

    deleteFormation(formation: Formation): Observable<Formation> {
        return this.http.delete<Formation>(`${this._backendURL.formations}/${formation.id}`);
      }
}
