import { Component, ViewEncapsulation } from '@angular/core';
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { Formation } from '../../../shared/types/modules.types';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, NgForOf, NgIf } from '@angular/common';
import { UserService } from '../../../../services/user.service';
import { User } from '../../../shared/types/user.type';

@Component({
  selector: 'app-add-formation-dialog',
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
  templateUrl: './add-formation-dialog.component.html',
  styleUrl: './add-formation-dialog.component.scss',
  encapsulation: ViewEncapsulation.None, // Désactive l'encapsulation pour appliquer les styles globaux

})
export class AddFormationDialogComponent {

  newFormation : Formation = { nom: '', responsableFormationId: -1, departementId: -1 };
  public users: User[] = [];

  constructor(public dialogRef: MatDialogRef<AddFormationDialogComponent>
  ,private userService: UserService) {
    this.loadResponsables();
  }


  onAdd(): void {
    this.dialogRef.close(this.newFormation);
  }

  loadResponsables(): void {
    this.userService.searchUsersByRole('RESPONSABLE_DE_FORMATION').subscribe(
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
