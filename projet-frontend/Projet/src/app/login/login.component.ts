import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators  , ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: "./login.component.html",
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

    login() {
    // CALL API with username and password
    if (this.loginForm.invalid) return;

    alert('Calling backend to login');
  }
}

