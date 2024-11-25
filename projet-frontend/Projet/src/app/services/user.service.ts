import { LoginService } from './login.service';
import { Injectable } from '@angular/core';
import { User } from '../componenets/shared/types/user.type';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment.prod';
import { defaultIfEmpty, filter, map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

   // private property to store all backend URLs
   private readonly _backendURL: any;
   // private property to store default person
   private readonly _defaultUser: User;
   private readonly _responsableId: number = 1;

   constructor(private _http: HttpClient, private _loginService: LoginService) {
     this._defaultUser = {
       username: 'username',
       email: 'email@ema.il',
       roles: ['ENSEIGNANT'],
       password: 'Ed*lZ%0qiA'
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

     this._responsableId = this._loginService.connectUser();
     if (!this._responsableId) {
      throw new Error("Responsable is not authenticated or responsableId is missing");
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
       this._backendURL.oneUser.replace(':id', id)
     );
   }

   /**
    * Function to create a new person
    */
   create(user: User): Observable<any> {
      const body = {
        responsableId: this._responsableId,  // Ajoute le responsableId
        user: user  // Ajoute l'objet user
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
      responsableId: this._responsableId,  // Ajoute le responsableId
      user: user  // Ajoute l'objet user
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
        headers: new HttpHeaders({
          'Authorization': `Bearer ${this._loginService.getAuthToken()}`  // Utilisation du token du LoginService
        })
      })
      .pipe(map(() => id));
   }

  private _options(headerList: object = {}): any {
    const token = this._loginService.getAuthToken(); // Récupère le token depuis LoginService

    // Crée un objet pour les en-têtes
    const headers: { [key: string]: string } = {
      'Content-Type': 'application/json',
    };

    // Si le token est disponible, ajoute l'en-tête Authorization
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

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

  searchUsers(username: string): Observable<any[]> {
    const url = `${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.allUsers}/${username}`;
        console.log("back response " ,url)

    return this._http.get<any[]>(url);
  }
  searchUsersByRole(role: string): Observable<any[]> {
    const url = `${environment.backend.protocol}://${environment.backend.host}:${environment.backend.port}${environment.backend.endpoints.role}/${role}`;
    return this._http.get<any[]>(url);
  }
}
