import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "express";
import { environment } from "../../environments/environment.prod";
import { Observable } from "rxjs/internal/Observable";
import { Module } from "../components/shared/types/modules.types";

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    private readonly _backendURL: any;

    public userRoles = [];
    private authToken: string | null = null;
    constructor(private http: HttpClient, private router: Router) {
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

    getAllModules(): Observable<any> {
        return this.http.get<Module[]>(`${this._backendURL.modules}`);
    }
}
