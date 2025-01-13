import { Component } from '@angular/core';
import { UserDialogComponent } from '../components/shared/user-dialog/user-dialog.component';
import { filter, map, mergeMap } from 'rxjs';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { UserService } from '../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserComponent } from "../components/user/user.component";
import { User, UserRoleDto } from '../components/shared/types/user.type';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-user-update',
  standalone: true,
  imports: [UserComponent],
  templateUrl: './user-update.component.html',
  styleUrl: './user-update.component.scss'
})
export class UserUpdateComponent {
  // private property to store dialog reference
  private _userDialog: MatDialogRef<UserDialogComponent, User> | undefined;

  /**
   * Component constructor
   */
  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private _userService: UserService,
    private _dialog: MatDialog,
    private _loginService: LoginService
  ) {}

  /**
   * OnInit implementation
   */
  ngOnInit(): void {
    this._route.params
      .pipe(
        map((params: any) => params.id),
        mergeMap((id: string) => this._userService.fetchOne(id))
      )
      .subscribe((user: User) => this._initModal(user));
  }

  transformRolesToUserRoles(roles: string[], year: number): UserRoleDto[] {
    return roles.map(role => ({
      role: role as UserRoleDto['role'],
      year: year
    }));
  }

  /**
   * Initialize modal process
   */
  private _initModal(user: User): void {
    // create modal with initial data inside
    this._userDialog = this._dialog.open(UserDialogComponent, {
      width: '500px',
      disableClose: true,
      data: user,
    });

    // subscribe to afterClosed observable to set dialog status and do process
    this._userDialog
      .afterClosed()
      .pipe(
        filter((user: User | undefined) => !!user),
        map((user: User | undefined) => {
          // get user id
          const id = user?.id;
          // delete obsolete attributes in original object which are not required in the API
          delete user?.id;

          return { id, update: user };
        }),
        mergeMap((_: { id: any; update: any }) =>{
          console.log('update ', _.update)
          const userToSend: User = {
            ..._.update,
            roles: this.transformRolesToUserRoles(_.update.roles, this._loginService.currentYearId ?? 1)
            //roles: _.update.roles.map((_) => { console.log(_); return {yearId: this._loginService.currentYearId ?? 1, role: _}}),
          }
          console.log('after update ', userToSend)
          return this._userService.update(_.id, userToSend)
        }
        )
      )
      .subscribe({
        error: () => this._router.navigate(['/users']),
        complete: () => this._router.navigate(['/users']),
      });
  }
}
