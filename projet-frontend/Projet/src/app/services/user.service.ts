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
 
   constructor(private _http: HttpClient) {
     this._defaultUser = {
       username: 'username',
       email: 'email@ema.il',
       role: 'ENSEIGNANT', 
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
     return this._http.post<User>(
       this._backendURL.allUsers,
       user,
       this._options()
     );
   }
 
   /**
    * Function to update one person
    */
   update(id: string, user: User): Observable<any> {
     return this._http.put<User>(
       this._backendURL.oneUser.replace(':id', id),
       user,
       this._options()
     );
   }
 
   /**
    * Function to delete one person for current id
    */
   delete(id: number): Observable<number> {
     return this._http
       .delete(this._backendURL.oneUser.replace(':id', id))
       .pipe(map(() => id));
   }
 
   /**
    * Function to return request options
    */
   private _options(headerList: object = {}): any {
     return {
       headers: new HttpHeaders(
         Object.assign({ 'Content-Type': 'application/json' }, headerList)
       ),
     };
   }
}
