import {RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './componenets/login/login.component';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './componenets/dashboard/dashboard.component';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';

export const routes: Routes = [{ path: '', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'enseignants', component: EnseignantsComponent },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}