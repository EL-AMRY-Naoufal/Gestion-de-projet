import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { LoginService } from '../../services/login.service';
import { MenuComponent } from '../shared/menu/menu.component';
import { Year } from '../shared/types/year.type';
import { YearService } from '../../services/year-service';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { User } from '../shared/types/user.type';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MenuComponent,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatMenuModule,
    FormsModule,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  userRoles: string[] = [];
  years: Year[] = [];
  selectedYearId: number | undefined;

  username: string | null = null;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private yearService: YearService,
    private userService: UserService
  ) {
    this.userRoles = this.loginService.userRoles;
  }
  ngOnInit(): void {
    // Fetch available years
    this.yearService.getAllYears().subscribe((years) => {
      this.years = years;
      console.log('years: ', years);

      // Set initial selected year based on the currentYearId in YearService
      const currentYearId = this.yearService.currentYearId;
      this.selectedYearId = years.find((year) => year.id === currentYearId)?.id;
      console.log('ID de l’année courante :', this.selectedYearId);
    });


    this.userService.authentifiedUser.subscribe((user: User) => {
      this.username = user.username;
    })
  }

  navigateToUsers() {
    this.router.navigate(['/users']);
  }

  navigateToAffectations() {
    this.router.navigate(['/enseignants/affectations']);
  }

  // Navigate to the component to create affectations by the admin
  navigateToCreateAffectations() {
    this.router.navigate(['/admin/affectations']);
  }

  navigateToDashboard() {
    this.router.navigate(['/dashboard']);
  }

  logout() {
    this.loginService.logout();
    this.router.navigate(['']);
  }

  navigateToTeachers() {
    this.router.navigate(['/enseignants']);
  }

  navigateToModules() {
    this.router.navigate(['/modules'])
  }
}
