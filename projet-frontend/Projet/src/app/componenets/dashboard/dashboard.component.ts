import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuComponent } from '../menu/menu.component';
import { ListUsersComponent } from "../list-users/list-users.component";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DashboardComponent, CommonModule, MenuComponent, ListUsersComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

}
