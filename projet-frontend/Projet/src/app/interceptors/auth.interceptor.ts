import { Injectable, Provider } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private loginService: LoginService, private router: Router) {} // Inject UserService et Router

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.loginService.getAuthToken();

    if (token && !req.url.includes('authenticate')) { // Si c'est une requête d'authentification, on ne fourni pas le token
      const cloned = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(cloned).pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            // Si le serveur retourne une erreur 401, on déconnecte l'utilisateur et on le redirige vers la page de connexion car son token n'est plus valide
            this.loginService.logout();
            this.router.navigate(['/login']);
          }
          return throwError(error);
        })
      );
    } else {
      return next.handle(req);
    }
  }
}

export const authInterceptorProvider: Provider = 
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true };