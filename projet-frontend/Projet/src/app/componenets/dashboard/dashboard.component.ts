import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuComponent } from '../shared/menu/menu.component';
import { ListUsersComponent } from "../list-users/list-users.component";
import { SearchBarComponent } from "../shared/search-bar/search-bar.component";
import { TopNavbarComponent } from "../shared/top-navbar/top-navbar.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DashboardComponent, CommonModule, MenuComponent, ListUsersComponent, SearchBarComponent, TopNavbarComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

}
