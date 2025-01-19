import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.prod";
import { Niveau, Semestre } from "../types/modules.types";

@Injectable({
  providedIn: 'root'
})

export class SemestreService {
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
    
    getSemestresByOrientation(orientationId: number): Observable<Semestre[]> {
        return this.http.get<Semestre[]>(`${this._backendURL.semestres}/orientation/${orientationId}`)
    }

    getNiveauxByFormation(formationId: number): Observable<Niveau[]> {
        return this.http.get<Niveau[]>(`${this._backendURL.niveaux}/formation/${formationId}`);
    }
}