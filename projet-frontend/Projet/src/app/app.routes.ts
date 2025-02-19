import {RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { EnseignantsComponent } from './components/enseignants/enseignants.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ListUsersComponent } from './components/list-users/list-users.component';
import { UserComponent } from './components/user/user.component';
import { UserUpdateComponent } from './user-update/user-update.component';
import {AffectationListComponent} from "./components/affectation/affectation-enseignant/affectation-enseignant.component";
import {CreateAffectationComponent} from "./components/affectation/create-affectation/create-affectation.component";
import { ModulesComponent } from './components/modules/modules.component';

  // Route vers le dashboard
export const routes: Routes = [

  // Route par défaut (page d'accueil)
  { path: '', component: LoginComponent },

  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  // Route pour les modules
  { path: 'modules', component: ModulesComponent},
  // Route pour les profil d'utilisateur
  { path: 'users', component: ListUsersComponent },
  // Route pour afficher un profil d'utilisateur
  { path: 'user/:id', component: UserComponent },
  { path: 'edit/:id', component: UserUpdateComponent },
  { path: 'enseignants', component: EnseignantsComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  //route for my consulte-enseignant componentw
  { path: 'enseignants/affectations/:id', component: AffectationListComponent },
  { path: 'enseignants/affectations', component: AffectationListComponent },
  //Route pour les affectations des enseignants par l'admin
  {path: 'admin/affectations', component: CreateAffectationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
