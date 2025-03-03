import {Component} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../shared/types/user.type";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-add-departement-dialog',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './add-departement-dialog.component.html',
  styleUrl: './add-departement-dialog.component.scss'
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
