import { Injectable } from '@angular/core';
import { LoginService } from './login.service';
import { firstValueFrom } from 'rxjs';
import { ApiService } from './api-service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthInitService {
  constructor(
    private loginService: LoginService,
    private _api: ApiService,
    private _router: Router
  ) {}

  async init() {
    try {
        this._api.me().subscribe(
            {
              complete: () => {
                this.loginService.isAuthenticated = true;
              },
              error: () => {
                this.loginService.isAuthenticated = false;
                this._router.navigate(["/login"])
              }
            }
          );
    } catch (error) {
      this.loginService.isAuthenticated = false;
    }
  }
}