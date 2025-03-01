import { EnseignantDto } from './../../shared/types/enseignant.type';
import { Component, input, Input, OnInit } from '@angular/core';
import { EnseignantService } from '../../../services/enseignant.service';
import {NgForOf, NgIf} from "@angular/common";
import {AffectationType} from "../../shared/types/affectation.type";
import {LoginService} from "../../../services/login.service";
import { MenuComponent } from "../../shared/menu/menu.component";
import {ActivatedRoute, Router} from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../../services/api-service';
import { User } from '../../shared/types/user.type';

@Component({
  selector: 'app-affectation-list',
  standalone: true,
  templateUrl: 'affectation-enseignant.component.html',
  imports: [
    NgIf,
    NgForOf,
    MenuComponent,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule
],
  styleUrls: ['affectation-enseignant.component.scss']
})
export class AffectationListComponent implements OnInit {

  enseignantId!: string;  // Input to receive enseignantId from the parent (dialog)
  @Input() dialog: boolean = false;  // Input to receive enseignantId from the parent (dialog)


  affectations: AffectationType[] = [];

  //enseignantId!: string;
  userRoles: string[] = [];

  editingId: number | null = null;
  editedComment: string = '';

  constructor(private enseignantService: EnseignantService,
    private loginService: LoginService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private _api: ApiService) {
    this.userRoles = this.loginService.userRoles;
    console.log("cc")
  }

  ngOnInit(): void {
    if( this.dialog == false ) {
      this.enseignantId = this.activatedRoute.snapshot.paramMap.get('id') || this.loginService.connectUser() + '';
    }

    this.enseignantService.getAffectationsByEnseignantId("5").subscribe(
      (data) => {
        this.affectations = data;
        console.log('Affectations:', this.affectations);
      },
      (error) => {
        console.error('Erreur lors de la récupération des affectations', error);
      }
    );
  }

/*  getAffectations(): void {
    this.enseignantService.getAffectationsByEnseignantId(this.enseignantId).subscribe(
      data => {
        this.affectations = data;
      },
      error => {
        console.error('Erreur lors de la récupération des affectations :', error);
      }
    );
  }*/

  // Navigate to the component to create affectations by the admin
  navigateToCreateAffectations() {
    this.router.navigate(['/admin/affectations']);
  }

  startEditing(affectation: any) {
    this.editingId = affectation.id;
    this.editedComment = affectation.commentaire;
  }

  saveComment(affectation: any) {

    if(this.editedComment.length > 255) {
      return;
    }

    affectation.commentaire = this.editedComment;
    this._api.updateAffectation(affectation.id, 0, affectation.commentaire).subscribe((resp) => {
      affectation.commentaire = resp.commentaire;
    });
    this.editingId = null;
  }

  cancelEditing() {
    this.editingId = null;
  }


  @Input()
  set user(user: User) {
    console.log("Nouvel user reçu :", user);

    if (user?.id !== undefined) {
      this.enseignantId = user.id.toString();
    } else {
      console.warn("L'utilisateur n'a pas d'ID !");
      this.enseignantId = ''; // Ou une valeur par défaut
    }
  }



}
