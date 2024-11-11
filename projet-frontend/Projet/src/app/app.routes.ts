import {RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componenets/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './componenets/dashboard/dashboard.component';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';
import {AffectationListComponent} from "./componenets/affectation-enseignant/affectation-enseignant.component";

export const routes: Routes = [{ path: '', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'enseignants', component: EnseignantsComponent },
  //route for my consulte-enseignant component
  { path: 'enseignants/affectations', component: AffectationListComponent },
  { path: '**', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
