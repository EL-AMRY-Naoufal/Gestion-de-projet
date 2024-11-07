import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
 constructor(private router: Router) { }

  navigateToTeachers() {
    this.router.navigate(['/enseignants']);
  }
  navigateToDashboard() {
    this.router.navigate(['/dashboard']);
  }
}
