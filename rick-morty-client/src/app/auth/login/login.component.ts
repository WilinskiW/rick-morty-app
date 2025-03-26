import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  loginForm = new FormGroup({
    username: new FormControl(""),
    password: new FormControl("")
  });
  error = false;
  private authService = inject(AuthService);

  onSubmit() {
    const username = this.loginForm.value.username;
    const password = this.loginForm.value.password;

    console.log(username, password)

    if (!username || !password) { //guard
      return;
    }

    this.error = this.authService.loginUser({username, password});
  }
}
