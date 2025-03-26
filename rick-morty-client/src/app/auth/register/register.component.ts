import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-register',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  registerForm = new FormGroup({
    username: new FormControl(""),
    password: new FormControl("")
  });
  error = false;
  private authService = inject(AuthService);

  onSubmit() {
    const username = this.registerForm.value.username;
    const password = this.registerForm.value.password;

    if(!username || !password){ //guard
      return;
    }

    this.error = this.authService.registerUser({username, password});
  }
}
