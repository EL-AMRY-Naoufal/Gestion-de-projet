import { Component } from '@angular/core';
import { User } from '../shared/types/user.type';
import { UserDialogComponent } from '../shared/user-dialog/user-dialog.component';
import { UserService } from '../../services/user.service';
import { filter, map, mergeMap, Observable } from 'rxjs';
import { MatDialogRef, MatDialogModule, MatDialog } from '@angular/material/dialog';
import { UserCardComponent } from '../shared/user-card/user-card.component';
import { NgIf, NgSwitch, NgSwitchCase } from '@angular/common';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-list-users',
  standalone: true,
  imports: [MatDialogModule, UserCardComponent, NgSwitch, NgSwitchCase, NgIf,CommonModule],
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss'
})
export class ListUsersComponent {
    // private property to store people value
    private _listUsers: User[];
    // private property to store dialogStatus value
    private _dialogStatus: string;
    // private property to store dialog reference
    private _listUsersDialog: MatDialogRef<UserDialogComponent, User> | undefined;
    // private property to store view value
    private _view: string;

    /**
     * Component constructor
     */
    constructor(
      private _router: Router,
      private _usersService: UserService,
      private _dialog: MatDialog
    ) {
      this._listUsers = [];
      this._dialogStatus = 'inactive';
      this._view = 'card';
    }

    /**
     * Returns private property _listUsers
     */
    get listUsers(): User[] {
      return this._listUsers;
    }

    /**
     * Returns private property _dialogStatus
     */
    get dialogStatus(): string {
      return this._dialogStatus;
    }

    /**
     * Returns private property _view
     */
    get view(): string {
      return this._view;
    }

    /**
     * OnInit implementation
     */
    ngOnInit(): void {
      this._usersService
        .fetch()
        .subscribe({
          next: (listUsers: User[]) => {
            this._listUsers = listUsers;
            //console.log('Fetched users:', this._listUsers); // Log pour vérifier les données reçues
          },
          error: (err) => console.error('Error fetching users:', err) // Log pour capturer les erreurs
        });
    }

    /**
     * Function to delete one person
     */
    delete(user: User): void {
      this._usersService
        .delete(user.id as number)
        .subscribe(
          (id: number) =>
            (this._listUsers = this._listUsers.filter((u: User) => u.id !== id))
        );
    }

    /**
     * Function to display modal
     */
    showDialog(): void {
      // set dialog status
      this._dialogStatus = 'active';

      // open modal
      this._listUsersDialog = this._dialog.open(UserDialogComponent, {
        width: '500px',
        disableClose: true,
      });

      // subscribe to afterClosed observable to set dialog status and do process
      this._listUsersDialog
        .afterClosed()
        .pipe(
          filter((user: User | undefined) => !!user),
          map((user: User | undefined) => {
            // delete obsolete attributes in original object which are not required in the API
            delete user?.id;


            return user;
          }),
          mergeMap((user: User | undefined) => this._add(user))
        )
        .subscribe({
          next: (user: User) => (this._listUsers = this._listUsers.concat(user)),
          error: () => (this._dialogStatus = 'inactive'),
          complete: () => (this._dialogStatus = 'inactive'),
        });
    }

    /**
     * Function to switch view
     */
    switchView(): void {
      this._view = this._view === 'card' ? 'list' : 'card';
    }

    /**
     * Function to navigate to current person
     */
    navigate(id: string | undefined): void {
      this._router.navigate(['/user', id]);
    }

    /**
     * Add new user
     */
    private _add(user: User | undefined): Observable<User> {
      return this._usersService.create(user as User);
    }

}
