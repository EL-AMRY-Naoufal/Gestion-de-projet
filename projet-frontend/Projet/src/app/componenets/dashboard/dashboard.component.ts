import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuComponent } from '../menu/menu.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { BrowserModule } from '@angular/platform-browser';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DashboardComponent,CommonModule,MenuComponent,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatMenuModule,],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
 userRoles: string[] = [];


  constructor(private router: Router, private loginService: LoginService) { 
    this.userRoles = this.loginService.getUserRoles()
 }

  navigateToUsers() {
    this.router.navigate(['/users']);
  }

  navigateToDashboard() {
    this.router.navigate(['/dashboard']);
  }

  logout() {
   this.loginService.logout(); 
    this.router.navigate(['']);
  }
}
