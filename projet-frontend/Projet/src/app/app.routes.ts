import {RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componenets/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './componenets/dashboard/dashboard.component';
import { UserComponent } from './componenets/user/user.component';
import { ListUsersComponent } from './componenets/list-users/list-users.component';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';
import { UserUpdateComponent } from './user-update/user-update.component';


export const routes: Routes = [
  // Route par défaut (page d'accueil)
  { path: '', component: LoginComponent },

  // Route vers le dashboard
  { path: 'dashboard', component: DashboardComponent },

  // Route pour les profil d'utilisateur
  { path: 'users', component: ListUsersComponent },

  // Route pour afficher un profil d'utilisateur
  { path: 'user/:id', component: UserComponent },
  { path: 'edit/:id', component: UserUpdateComponent },

  { path: 'enseignants', component: EnseignantsComponent },


]



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
