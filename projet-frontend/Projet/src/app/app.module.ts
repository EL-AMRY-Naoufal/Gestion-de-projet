import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app.routes'
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {CommonModule, DatePipe} from '@angular/common';
import { EnseignantsComponent } from './components/enseignants/enseignants.component';
import {AffectationListComponent} from "./components/affectation/affectation-enseignant/affectation-enseignant.component";

@NgModule({
  declarations: [


  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
  //  ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
