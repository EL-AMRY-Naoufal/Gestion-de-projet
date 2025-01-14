import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../services/login.service';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule, MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatMenuModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
userRoles: string[] = [];

  constructor(private router: Router, private loginService: LoginService) {
    this.userRoles = this.loginService.userRoles;
 }

  navigateToTeachers() {
    this.router.navigate(['/enseignants']);
  }

  navigateToDashboard() {
    this.router.navigate(['/dashboard']);
  }


  navigateToUsers() {
    this.router.navigate(['/users']);
  }


  logout() {
   this.loginService.logout();
    this.router.navigate(['']);
  }
  navigateToAffectations() {
    this.router.navigate(['/enseignant/MesAffectations']);
  }
}
