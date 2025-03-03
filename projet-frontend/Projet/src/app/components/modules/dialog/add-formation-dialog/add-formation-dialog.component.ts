import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {FormsModule} from "@angular/forms";
import { Formation } from '../../../shared/types/modules.types';
import {NgForOf} from "@angular/common";
import {User} from "../../../shared/types/user.type";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-add-formation-dialog',
  standalone: true,
    imports: [
        FormsModule,
        NgForOf
    ],
  templateUrl: './add-formation-dialog.component.html',
  styleUrl: './add-formation-dialog.component.scss'
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
