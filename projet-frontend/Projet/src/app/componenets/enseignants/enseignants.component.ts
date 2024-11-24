import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../shared/menu/menu.component';
import { UserService } from '../../services/user.service';
import { UserCardComponent } from '../card/user-card/user-card.component';
import { User } from '../shared/types/user.type';

@Component({
  selector: 'app-enseignants',
  standalone: true,
  imports: [CommonModule,MenuComponent,UserCardComponent],
  templateUrl: './enseignants.component.html',
  styleUrl: './enseignants.component.scss'
})
export class EnseignantsComponent  implements OnInit{
 users: any[] = [];
  constructor(private userService: UserService,  private _usersService: UserService,) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(data => {
      this.users = data;
      //console.log("users", this.users);
    });  }



}
