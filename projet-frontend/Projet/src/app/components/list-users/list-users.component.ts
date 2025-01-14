import { Component } from '@angular/core';
import { UserDialogComponent } from '../shared/user-dialog/user-dialog.component';
import { UserService } from '../../services/user.service';
import { filter, map, mergeMap, Observable } from 'rxjs';
import {
  MatDialogRef,
  MatDialogModule,
  MatDialog,
} from '@angular/material/dialog';
import { UserCardComponent } from '../shared/user-card/user-card.component';
import { NgIf, NgSwitch, NgSwitchCase } from '@angular/common';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MenuComponent } from '../shared/menu/menu.component';
import { CategorieEnseignant, EnseignantDto } from '../shared/types/enseignant.type';
import { EnseignantService } from '../../services/enseignant.service';
import { Roles, User } from '../shared/types/user.type';
import { YearService } from '../../services/year-service';


@Component({
  selector: 'app-list-users',
  standalone: true,
  imports: [
    MatDialogModule,
    UserCardComponent,
    NgSwitch,
    NgSwitchCase,
    NgIf,
    CommonModule,
    FormsModule,
    MenuComponent,
],
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss',
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

  searchQuery: string = '';
  selectedRole: string = '';
  _user!: User;
  enseignantDto: EnseignantDto = {
    categorieEnseignant: CategorieEnseignant.PROFESSEUR,
    nbHeureCategorie: 0,
    maxHeuresService: 0,
    heuresAssignees: 0
  }
  roles: string[] = [
    'CHEF_DE_DEPARTEMENT',
    'RESPONSABLE_DE_FORMATION',
    'SECRETARIAT_PEDAGOGIQUE',
    'ENSEIGNANT',
  ];

  /**
   * Component constructor
   */
  constructor(
    private _router: Router,
    private _usersService: UserService,
    private _dialog: MatDialog,
    private _enseignantService: EnseignantService,
    private _yearService: YearService
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
    this._usersService.fetch().subscribe({
      next: (listUsers: User[]) => {
        // Vérifier que la réponse est bien un tableau
        if (Array.isArray(listUsers)) {
          this._listUsers = listUsers;
        } else {
          console.error("La réponse n'est pas un tableau:", listUsers);
          this._listUsers = []; // Assurer que _listUsers est un tableau vide en cas d'erreur
        }
      },
      error: (err) => {
        console.error('Error fetching users:', err);
        this._listUsers = []; // En cas d'erreur, assigner un tableau vide
      },
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
          this.enseignantDto.categorieEnseignant = user?.categorieEnseignant as CategorieEnseignant;
          this.enseignantDto.nbHeureCategorie = user?.nbHeureCategorie as number;
          this.enseignantDto.maxHeuresService = user?.maxHeuresService as number;
          delete user?.categorieEnseignant;
          delete user?.maxHeuresService;
          delete user?.nbHeureCategorie;


          return user;
        }),
        mergeMap((user: User | undefined) => this._add(user))
      )
      .subscribe({
        next: (user: User) => {
          (this._listUsers = this._listUsers.concat(user));
          if (this._usersService.userHasRole(user, 'ENSEIGNANT', this._loginService.currentYearId)) {
            this.enseignantDto.id = user.id; this._addTeacher(this.enseignantDto) } },
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
    if (!user) {
      return new Observable<User>();
    }

    const userToSend: User = {
      ...user,
<<<<<<< HEAD
      roles: user.roles.map((role) => { return {yearId: this._yearService.currentYearId, role: role as unknown as Roles}}),
=======
      roles: user.roles.map((role) => { return {year: this._loginService.currentYearId ?? 1, role: role as unknown as Roles}}),
>>>>>>> df9f2bb36f2442c93cb37fabba75ce1547c96d7f
    }

    return this._usersService.create(userToSend);
  }

  private _addTeacher(enseignantDto: EnseignantDto) {
    console.log("enseignant ", enseignantDto)
    this._enseignantService.createEnseignant(enseignantDto).subscribe(

    );
  }

  searchTeachers(): void {
    if (this.searchQuery.trim()) {
      this._usersService.searchUsers(this.searchQuery.trim()).subscribe({
        next: (data: User[]) => {
          if (Array.isArray(data)) {
            this._listUsers = data;
          } else {
            console.error(
              "La réponse de la recherche n'est pas un tableau:",
              data
            );
            this._listUsers = [];
          }
        },
        error: (err) => {
          console.error('Erreur lors de la recherche des utilisateurs:', err);
          this._listUsers = [];
        },
      });
    } else {
      this.ngOnInit();
    }
  }

  filterByRole() {
    if (this.selectedRole) {
      this._usersService
        .searchUsersByRole(this.selectedRole)
        .subscribe((data) => {
          this._listUsers = data;
        });
    } else {
      this.listUsers;
    }
  }
}
