import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app.routes'

import { AppComponent } from './app.component';
import { LoginComponent } from './componenets/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { EnseignantsComponent } from './componenets/enseignants/enseignants.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    EnseignantsComponent
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
    HttpClientModule,
    CommonModule,
  //  ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }