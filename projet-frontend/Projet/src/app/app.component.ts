import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuComponent } from './components/shared/menu/menu.component';
import { CommonModule } from '@angular/common';
import { LoginService } from './services/login.service';



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MenuComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  private _isAuthenticated: boolean = false;
  title = 'Projet';

  constructor(private _loginService: LoginService) {
    this._loginService.isAuthenticated$.subscribe((isAuthenticated) => {
      this._isAuthenticated = isAuthenticated;
    });
  }

  get isAuthenticated(): boolean {
    return this._isAuthenticated;
  }
}
