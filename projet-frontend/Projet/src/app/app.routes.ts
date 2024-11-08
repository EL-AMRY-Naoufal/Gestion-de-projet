import {RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componenets/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './componenets/dashboard/dashboard.component';
import { UserComponent } from './componenets/user/user.component';
import { ListUsersComponent } from './componenets/list-users/list-users.component';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';


export const routes: Routes = [
  // Route par défaut (page d'accueil)
  { path: 'login', component: LoginComponent },

  // Route vers le dashboard
  { path: 'dashboard', component: DashboardComponent },

  // Route pour les profil d'utilisateur
  { path: 'users', component: ListUsersComponent },

  // Route pour afficher un profil d'utilisateur
  { path: 'user/:id', component: UserComponent },

  { path: 'enseignants', component: EnseignantsComponent },


]


/*
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';
import {AffectationListComponent} from "./componenets/affectation-enseignant/affectation-enseignant.component";

export const routes: Routes = [{ path: '', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'enseignants', component: EnseignantsComponent },
  //route for my consulte-enseignant component
  { path: 'enseignants/affectations', component: AffectationListComponent },
];

*/

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
