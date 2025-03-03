import { CommonModule, NgForOf, NgIf } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatOptionModule } from '@angular/material/core';
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {UserService} from "../../../../services/user.service";
import {User} from "../../../shared/types/user.type";

@Component({
  selector: 'app-add-departement-dialog',
  standalone: true,
    imports: [
        FormsModule,
        NgForOf,
        NgIf,
        CommonModule,
        FormsModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatOptionModule,
        MatCheckboxModule,
        ReactiveFormsModule,
        MatDialogModule,
    ],
  templateUrl: './add-departement-dialog.component.html',
  styleUrl: './add-departement-dialog.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class AddDepartementDialogComponent {

  newDepartement = {nom: '', responsableDepartementId: -1, anneeId: -1};
  public users: User[] = [];

  constructor(public dialogRef: MatDialogRef<AddDepartementDialogComponent>,
              private userService: UserService) {
    this.loadResponsables();
  }

  onAdd(): void {
    this.dialogRef.close(this.newDepartement);
  }

  loadResponsables(): void {
    this.userService.searchUsersByRole('CHEF_DE_DEPARTEMENT').subscribe(
      (data: User[]) => {
        console.log(data);
        this.users = data;
      },
      (error) => console.error('Erreur lors du chargement des responsables', error)
    );
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
