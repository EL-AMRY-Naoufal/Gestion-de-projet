import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app.routes'
import { AppComponent } from './app.component';
import { LoginComponent } from './componenets/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {CommonModule, DatePipe} from '@angular/common';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';
import {AffectationListComponent} from "./componenets/affectation-enseignant/affectation-enseignant.component";

@NgModule({
  declarations: [
    AppComponent,
    AffectationListComponent,
    LoginComponent,
    EnseignantsComponent

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
  bootstrap: [AppComponent]
})
export class AppModule { }
