import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';  // Pour utiliser les liens de navigation
import { MatCardModule } from '@angular/material/card';  // Pour le composant mat-card
import { MatIconModule } from '@angular/material/icon';  // Pour les icônes
import { MatButtonModule } from '@angular/material/button';  // Pour les boutons
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../services/login.service';
import { User } from '../types/user.type';


@Component({
  selector: 'app-user-card',
  standalone: true,
  imports: [
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    CommonModule,
  ],
  templateUrl: './user-card.component.html',
  styleUrl: './user-card.component.scss'
})
export class UserCardComponent {
    // private property to store user value
    private _user: User;
    // private property to store delete$ value
    private readonly _delete$: EventEmitter<User>;

    /**
     * Component constructor
     */
    constructor(private loginService: LoginService) {
      this._user = {} as User;
      this._delete$ = new EventEmitter<User>();
    }

    /**
     * Returns private property _person
     */
    get user(): User {

      return this._user;
    }

    /**
     * Sets private property _user
     */
    @Input()
    set user(user: User) {
      this._user = user;
    }

    /**
     * Returns private property _delete$
     */
    @Output('deleteUser') get delete$(): EventEmitter<User> {
      return this._delete$;
    }

    /**
     * OnInit implementation
     */
    ngOnInit(): void {}

    /**
     * Function to emit event to delete current user
     */
    delete(user: User): void {
      this._delete$.emit(user);
    }

    isChefDepartement(): boolean {
      return this.user.roles.filter(role => role.yearId === this.loginService.currentYearId && role.role === 'CHEF_DE_DEPARTEMENT').length > 0;
    }



}
