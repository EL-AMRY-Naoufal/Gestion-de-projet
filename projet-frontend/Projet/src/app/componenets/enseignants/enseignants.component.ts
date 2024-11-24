import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../menu/menu.component';
import { UserService } from '../../services/user.service';
import { UserCardComponent } from '../card/user-card/user-card.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-enseignants',
  standalone: true,
  imports: [CommonModule,MenuComponent,UserCardComponent , FormsModule],
  templateUrl: './enseignants.component.html',
  styleUrl: './enseignants.component.scss'
})
export class EnseignantsComponent  implements OnInit{
  users: any[] = [];
  searchQuery: string = '';

  roles: string[] = [
    'CHEF_DE_DEPARTEMENT',
    'RESPONSABLE_DE_FORMATION',
    'SECRETARIAT_PEDAGOGIQUE',
    'ENSEIGNANT'
  ];
  selectedRole: string = '';
  
  constructor(private userService: UserService) { }
  
  ngOnInit(): void {
    this.getUsers();
  }

   getUsers(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
    });
  }
  searchTeachers(): void {
    if (this.searchQuery.trim()) {  
      this.userService.searchUsers(this.searchQuery).subscribe(data => {
        this.users = data ? [data] : []; 
      });
    } else {
      this.getUsers(); 
    }
  }

  filterByRole() {
    if (this.selectedRole) {
      this.userService.searchUsersByRole(this.selectedRole).subscribe(data => {
        this.users = data;
        })
    } else {
      this.getUsers();  
    }
}
}
