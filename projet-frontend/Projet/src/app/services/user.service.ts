import { LoginService } from './login.service';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { defaultIfEmpty, filter, map, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../components/shared/types/user.type';
import { YearService } from './year-service';
import { isPlatformBrowser } from '@angular/common';
import { ApiService } from './api-service';
import { EnseignantDto } from '../components/shared/types/enseignant.type';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  // private property to store all backend URLs
  private readonly _backendURL: any;
  // private property to store default person
  private readonly _defaultUser: User;
  private readonly _responsableId: number = 1;

  private readonly isBrowser!: boolean;

  constructor(
    private _http: HttpClient,
    private _loginService: LoginService,
    private _yearService: YearService,
    private _api: ApiService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);

    this._defaultUser = {
      username: 'username',
      firstname: 'firstname',
      name: 'lastname',
      email: 'email@etu.univ-lorraine.fr',
      roles: [],
      password: '',
    };
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

    this._responsableId = this._loginService.connectUser() ?? 0;
    if (!this._responsableId) {
      throw new Error(
        'Responsable is not authenticated or responsableId is missing'
      );
    }
  }

  /**
   * Returns private property _defaultPerson
   */
  get defaultUser(): User {
    return this._defaultUser;
  }

  /**
   * Function to return list of person
   */
  fetch(): Observable<User[]> {
    return this._http.get<User[]>(this._backendURL.allUsers).pipe(
      filter((user: User[]) => !!user),
      defaultIfEmpty([])
    );
  }

  /**
   * Function to return one person for current id
   */
  fetchOne(id: string): Observable<User> {
    return this._http.get<User>(
      this._backendURL.oneUser.replace(':id', `id/${id}`)
    );
  }

  /**
   * Function to create a new person
   */
  create(user: User): Observable<any> {
    const newUser = {
      ...user,
    };

    const body = {
      responsableId: this._responsableId, // Ajoute le responsableId
      user: newUser, // Ajoute l'objet user
      associateEnseignantWithUser: user.hasProfile,
      year: 1,
    };

    return this._http.post<User>(
      this._backendURL.allUsers,
      body,
      this._options()
    );
  }

  /**
   * Function to update one person
   */
  update(id: string, user: User): Observable<any> {
    const body = {
      responsableId: this._responsableId, // Ajoute le responsableId
      user: user, // Ajoute l'objet user
      associateEnseignantWithUser: false,
      year: this._yearService.currentYearId,
    };
    return this._http.put<User>(
      this._backendURL.oneUser.replace(':id', id),
      body,
      this._options()
    );
  }

  /**
   * Function to delete one person for current id
   */
  delete(id: number): Observable<number> {
    return this._http
      .delete<number>(this._backendURL.oneUser.replace(':id', id.toString()), {
        body: { responsableId: this._responsableId },
      })
      .pipe(map(() => id));
  }

  private _options(headerList: object = {}): any {
    // Crée un objet pour les en-têtes
    const headers: { [key: string]: string } = {
      'Content-Type': 'application/json',
    };

    // Fusionne les en-têtes supplémentaires (headerList) avec ceux déjà définis
    return {
      headers: new HttpHeaders({
        ...headers, // Ajoute les en-têtes de base
        ...headerList, // Fusionne les autres en-têtes si nécessaires
      }),
    };
  }

  getUsers(): Observable<any[]> {
    return this._http.get<any[]>(this._backendURL.allUsers);
  }



  updateAffectation(affectationId: number, nombreHeure: number, anneeId: number): Observable<any> {
    return this._http
      .put(
        `${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.affectations}/${affectationId}/${nombreHeure}/${anneeId}`,
        null,
        { responseType: 'text' }
      )
      .pipe(
        map((response) => {
          try {
            return JSON.parse(response);
          } catch (e) {
            return response;
          }
        }),
        catchError((error) => {
          console.error("Erreur lors de la mise à jour de l'affectation :", error);
          return throwError(() => new Error('Une erreur est survenue.'));
        })
      );
  }

  deleteAffectation(affectationId: number, anneeId: number): Observable<string> {
    return this._http
      .delete<string>(`${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.affectations}/${affectationId}/${anneeId}`, )
      .pipe(map((response) => response));
  }

  searchUsers(query: string): Observable<any[]> {
    const url = `${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.allUsers}?q=${query}`;
    return this._http.get<any[]>(url);
  }
  searchUsersByRoleAndYear(role: string, year: number, query: string): Observable<any[]> {
    const url = `${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.role}?role=${role}&year=${year}&q=${query}`;
    console.log('role', url);
    return this._http.get<any[]>(url);
  }

  searchUsersByRole(role: string): Observable<User[]> {
    const url = `${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.justRole}?role=${role}`;

    return this._http.get<User[]>(url);
  }

  getRoleByUserIdAndYear(userId: number, year: number) {
    const url = `${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.allUsers}/user/${userId}/year/${year}`;

    return this._http.get<any[]>(url);
  }

  userHasRole(user: User, role: string, year: number | null): boolean {
    return (
      user.roles.filter((r) => r.role === role && r.year == year).length > 0
    );
  }

  get authentifiedUser(): Observable<User> {
    return this._api.me();
  }
}
