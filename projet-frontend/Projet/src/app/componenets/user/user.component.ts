import { Component } from '@angular/core';
import { UserCardComponent } from "../shared/user-card/user-card.component";
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { User } from '../shared/types/user.type';
import { filter, merge, mergeMap, tap } from 'rxjs';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [UserCardComponent],
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent {
    /**
   * Component constructor
   */
    constructor(
      private _userService: UserService,
      private _route: ActivatedRoute
    ) {
      this._user = {} as User;
      this._isUser = false;
    }
  
    // private property to store user value
    private _user: User;
  
    /**
     * Returns private property _user
     */
    get user(): User {
      return this._user;
    }
  
    // private property to store flag to know if it's a user
    private _isUser: boolean;
  
    /**
     * Returns flag to know if we are on a profile or on HP
     */
    get isUser(): boolean {
      return this._isUser;
    }
  
    /**
     * OnInit implementation
     */
    ngOnInit(): void {
      merge(
        this._route.params.pipe(
          filter((params: any) => !!params.id),
          mergeMap((params: any) => this._userService.fetchOne(params.id)),
          tap(() => (this._isUser = true))
        ),
      ).subscribe({
        next: (user: User) => (this._user = user),
        error: () => {
          // manage error when user doesn't exist in DB
          this._user = this._userService.defaultUser;
          this._isUser = true;
        },
      });
    }
  

}
