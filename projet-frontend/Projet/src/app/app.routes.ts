import {RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componenets/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './componenets/dashboard/dashboard.component';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';
import { ResetPasswordComponent } from './componenets/reset-password/reset-password.component';
import {AffectationListComponent} from "./componenets/affectation-enseignant/affectation-enseignant.component";

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'enseignants', component: EnseignantsComponent },
  { path: 'reset-password', component: ResetPasswordComponent }
  //route for my consulte-enseignant component
  { path: 'enseignants/affectations', component: AffectationListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
