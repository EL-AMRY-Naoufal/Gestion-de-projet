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
import { Year } from '../types/year.type';
import { User } from '../types/user.type';
import { UserService } from '../../../services/user.service';
import { YearService } from '../../../services/year-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    CommonModule,
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
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss',
})
export class MenuComponent {
  userRoles: string[] = [];
  private _listUsers: User[];
  years: Year[] = [];
  selectedYearId: number | null = null;
  selectedYear: Year | null = null;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private _yearService: YearService
  ) {
    this.userRoles = this.loginService.userRoles;
    this._listUsers = [];
  }

  ngOnInit(): void {
      this._yearService.getAllYears().subscribe((years) => {
        this.years = years;
        this.selectedYearId = this._yearService.currentYearId ?? null;
      });

      this._yearService.selectedYear$.subscribe((year) => {
        this.selectedYear = year;
        this.selectedYearId = year?.id ?? null;
      });
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
    this.router.navigate(['/enseignants/affectations']);
  }

  onYearSelected(): void {
    const yearId = Number(this.selectedYearId);
    this.selectedYear = this.years.find((year) => year.id === yearId) ?? null;
    if (this.selectedYear) {
      this._yearService.setSelectedYear(this.selectedYear); // Mettre à jour l'année sélectionnée
    }
  }
}
