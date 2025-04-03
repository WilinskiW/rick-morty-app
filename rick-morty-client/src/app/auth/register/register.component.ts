import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';
import { FormService } from '../../form.service';

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
    username: new FormControl("", {
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(255)]
    }),
    password: new FormControl("", {
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(255)]
    })
  });
  error = false;
  private authService = inject(AuthService);
  private formService = inject(FormService);

  isInvalid(key: string): boolean {
    return this.formService.isInvalid(this.registerForm, key);
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.formService.markAllControlsAsTouched(this.registerForm);
      return;
    }

    const username = this.registerForm.value.username;
    const password = this.registerForm.value.password;

    if (!username || !password) {
      return;
    }

    this.authService.registerUser({username, password})
      .subscribe();
  }
}
