import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment.prod";
import {Observable} from "rxjs";
import { Module } from "../components/shared/types/modules.types";

@Injectable({
  providedIn: 'root'
})
export class ModuleService {
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

  getAllModules(): Observable<any> {
    return this.http.get<Module[]>(`${this._backendURL.modules}`);
  }

  saveModule(module: Module): Observable<Module> {
          return this.http.post<Module>(`${this._backendURL.modules}`, module);
  }

  getModuleById(id: number): Observable<Module> {
    return this.http.get<Module>(`${this._backendURL.modules}/${id}`);
  }

  deleteModule(module: Module): Observable<Module> {
    return this.http.delete<Module>(`${this._backendURL.modules}/${module.id}`);
}
}
