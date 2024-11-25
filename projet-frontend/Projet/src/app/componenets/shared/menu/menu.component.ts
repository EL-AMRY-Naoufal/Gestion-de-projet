import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../services/login.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
userRoles: string[] = [];

  constructor(private router: Router, private loginService: LoginService) {
    this.userRoles = this.loginService.getUserRoles()
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
}
