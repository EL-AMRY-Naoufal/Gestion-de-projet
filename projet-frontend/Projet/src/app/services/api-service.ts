import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Year } from "../components/shared/types/year.type";

@Injectable({
    providedIn: 'root'
})
export class ApiService {

    private readonly _backendURL: any;

    constructor(private _http: HttpClient) {
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

    allYears(): Observable<Year[]> {
        return this._http.get<Year[]>(this._backendURL.allYears);
    }

    updateAffectation(idAffectation: number, idEnseignant: number, comment: string): Observable<any> {
        return this._http.put<any>(`${this._backendURL.allEnseignants}/${idEnseignant}/affectations/${idAffectation}/commentaire`, {
            commentaire: comment
        });
    }

}