import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MenuComponent } from '../menu/menu.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DashboardComponent,CommonModule,MenuComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
 
}
