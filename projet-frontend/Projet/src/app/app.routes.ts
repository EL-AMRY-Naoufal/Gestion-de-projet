import {RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componenets/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './componenets/dashboard/dashboard.component';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';
import { ResetPasswordComponent } from './componenets/reset-password/reset-password.component';
import { ListUsersComponent } from './componenets/list-users/list-users.component';
import { UserComponent } from './componenets/user/user.component';
import { UserUpdateComponent } from './user-update/user-update.component';
import {AffectationListComponent} from "./componenets/affectation/affectation-enseignant/affectation-enseignant.component";
import {CreateAffectationComponent} from "./componenets/affectation/create-affectation/create-affectation.component";
import { ModulesComponent } from './componenets/modules/modules.component';


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
  // Route pour les Affections des enseignants
  {path: 'enseignant/MesAffectations', component: AffectationListComponent},
  { path: 'enseignants', component: EnseignantsComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  //route for my consulte-enseignant componentw
  { path: 'enseignants/affectations', component: AffectationListComponent },
  //Route pour les affectations des enseignants par l'admin
  {path: 'admin/affectations', component: CreateAffectationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
