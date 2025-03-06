import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.prod";
import { Niveau } from "../components/shared/types/modules.types";

@Injectable({
  providedIn: 'root'
})

export class NiveauService {
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

    getAllNiveaux(): Observable<Niveau[]> {
        return this.http.get<Niveau[]>(`${this._backendURL.niveaux}`);
    }

    getNiveauxByFormation(formationId: number): Observable<Niveau[]> {
        return this.http.get<Niveau[]>(`${this._backendURL.niveaux}/formation/${formationId}`);
    }

    saveNiveau(niveau: Niveau): Observable<Niveau> {
        return this.http.post<Niveau>(`${this._backendURL.niveaux}`, niveau);
    }

    deleteNiveau(niveau: Niveau): Observable<Niveau> {
        return this.http.delete<Niveau>(`${this._backendURL.niveaux}/${niveau.id}`);
    }

    isNiveau(obj: any): obj is Niveau {
        return obj && typeof obj.formationId === 'number';
    }
}
