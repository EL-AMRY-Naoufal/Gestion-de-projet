import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { Groupe } from "../components/shared/types/modules.types";

@Injectable({
  providedIn: 'root'
})

export class GroupeService {
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

    getAllGroupes(): Observable<Groupe[]> {
        return this.http.get<Groupe[]>(`${this._backendURL.groupes}`);
    }

    getGroupesByModule(moduleId: number): Observable<Groupe[]> {
        return this.http.get<Groupe[]>(`${this._backendURL.groupes}/module/${moduleId}`);
    }

    saveGroupe(groupe: Groupe): Observable<Groupe> {
        return this.http.post<Groupe>(`${this._backendURL.groupes}`, groupe);
    }

    getGroupeById(id: number): Observable<Groupe> {
        return this.http.get<Groupe>(`${this._backendURL.groupes}/${id}`);
    }

    deleteGroupe(groupe: Groupe): Observable<Groupe> {
        return this.http.delete<Groupe>(`${this._backendURL.groupes}/${groupe.id}`);
    }

    isGroupe(obj: any): obj is Groupe {
    return obj && typeof obj.moduleId === 'number';
    }
}
