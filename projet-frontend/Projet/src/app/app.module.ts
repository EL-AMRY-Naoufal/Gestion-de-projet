import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app.routes'
import { AppComponent } from './app.component';
import { LoginComponent } from './componenets/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog'; // Si ce n'est pas déjà fait
import { UserDialogComponent } from './componenets/shared/user-dialog/user-dialog.component';
import { UserCardComponent } from './componenets/shared/user-card/user-card.component';
import { UserFormComponent } from './componenets/shared/user-form/user-form.component';
import { UserComponent } from './componenets/user/user.component';
import { ListUsersComponent } from './componenets/list-users/list-users.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterLink } from '@angular/router';
import {CommonModule, DatePipe} from '@angular/common';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';
import {AffectationListComponent} from "./componenets/affectation-enseignant/affectation-enseignant.component";

@NgModule({
  declarations: [
    AppComponent,
    AffectationListComponent,
    LoginComponent,
    UserDialogComponent,
    UserCardComponent,
    UserFormComponent,
    UserComponent,
    ListUsersComponent,
    EnseignantsComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    MatFormFieldModule, // Ajoutez ici
    MatInputModule,      // Ajoutez ici
    MatSelectModule,     // Ajoutez ici
    MatDialogModule,     // Si vous ne l'avez pas encore ajouté
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    RouterLink,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
