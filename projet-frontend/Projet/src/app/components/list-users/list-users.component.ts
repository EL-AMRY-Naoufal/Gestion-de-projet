import { Component, OnInit } from '@angular/core';
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
import {
  CategorieEnseignant,
  EnseignantDto,
} from '../shared/types/enseignant.type';
import { EnseignantService } from '../../services/enseignant.service';
import { Roles, User } from '../shared/types/user.type';
import { YearService } from '../../services/year-service';
import { Year } from '../shared/types/year.type';
import { LoginService } from '../../services/login.service';
import { EnseignantsComponent } from '../enseignants/enseignants.component';

@Component({
  selector: 'app-list-users',
  standalone: true,
  imports: [
    MatDialogModule,
    UserCardComponent,
    NgSwitch,
    NgSwitchCase,
    CommonModule,
    FormsModule,
    MenuComponent,
    EnseignantsComponent,
  ],
  templateUrl: './list-users.component.html',
  styleUrl: './list-users.component.scss',
})
export class ListUsersComponent implements OnInit {
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
    categorieEnseignant: CategorieEnseignant.ATER,
    nbHeureCategorie: 0,
    maxHeuresService: 0,
    heuresAssignees: 0,
  };
  roles: string[] = [
    'CHEF_DE_DEPARTEMENT',
    'RESPONSABLE_DE_FORMATION',
    'SECRETARIAT_PEDAGOGIQUE',
    'ENSEIGNANT',
  ];
  private _loginService: any;

  userRoles: string[] = [];
  years: Year[] = [];
  selectedYearId: number | null = null;
  selectedYear: Year | null = null;

  /**
   * Component constructor
   */
  constructor(
    private _router: Router,
    private _usersService: UserService,
    private _dialog: MatDialog,
    private _enseignantService: EnseignantService,
    private loginService: LoginService,
    private _yearService: YearService
  ) {
    this.userRoles = this.loginService.userRoles;
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

    this._yearService.selectedYear$.subscribe((year) => {
      this.selectedYear = year;
      if (year !== null) {
        this.onYearChange();
      }
    });
  }

  onYearChange(): void {
    const yearId = Number(this.selectedYearId);
    console.log(' this.onchange ', this.selectedYear);

    if (this.selectedYear) {
      // Pour chaque utilisateur de la liste, récupérer les rôles pour l'année sélectionnée
      this._listUsers.forEach((user) => {
        this._usersService
          .getRoleByUserIdAndYear(user.id as number, this.selectedYear?.id ?? 1)
          .subscribe({
            next: (roles: any[]) => {
              user.roles = roles.map((roleDto) => ({
                ...roleDto,
                role: roleDto.role,
                year: this.selectedYear?.debut,
              }));
            },
            error: (err: any) => {
              console.error(
                `Erreur lors de la récupération des rôles pour l'utilisateur ${user.username}:`,
                err
              );
            },
          });
      });
    }
  }

  getSelectedYear() {
    if (this.selectedYear != null) {
      return this.selectedYear.debut;
    }
    return 2021;
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
          this.enseignantDto.categorieEnseignant =
            user?.categorieEnseignant as CategorieEnseignant;
          this.enseignantDto.nbHeureCategorie =
            user?.nbHeureCategorie as number;
          this.enseignantDto.maxHeuresService =
            user?.maxHeuresService as number;
          delete user?.categorieEnseignant;
          delete user?.maxHeuresService;
          delete user?.nbHeureCategorie;

          return user;
        }),
        mergeMap((user: User | undefined) => this._add(user))
      )
      .subscribe({
        next: (user: User) => {
          this._listUsers = this._listUsers.concat(user);
          if (
            this._usersService.userHasRole(
              user,
              'ENSEIGNANT',
              this._yearService.currentYearId
            )
          ) {
            this.enseignantDto.id = user.id;
            this._addTeacher(this.enseignantDto);
          }
        },
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
      roles: user.roles.map((role) => {
        return {
          year: this._yearService.currentYearId ?? 1,
          role: role as unknown as Roles,
        };
      }),
    };

    return this._usersService.create(userToSend);
  }

  private _addTeacher(enseignantDto: EnseignantDto) {
    console.log('enseignant ', enseignantDto);
    this._enseignantService.createEnseignant(enseignantDto).subscribe();
  }

  searchTeachers(): void {
    const normalizedQuery = this.normalizeString(this.searchQuery.trim());

    if (normalizedQuery) {
      this._usersService.searchUsers(normalizedQuery).subscribe({
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
  private normalizeString(str: string): string {
    return str
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/[^a-z0-9]/g, '');
  }

  filterByRole() {
    if (this.selectedRole && this.selectedYear) {
      this._usersService
        .searchUsersByRoleAndYear(this.selectedRole, this.selectedYear?.id)
        .subscribe((data) => {
          this._listUsers = data;
        });
    } else {
      this._listUsers = [];
    }
  }
}
