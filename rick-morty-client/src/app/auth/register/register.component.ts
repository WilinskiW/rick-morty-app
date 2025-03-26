import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';

interface UserCredential {
  username: string,
  password: string
}

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
  private httpClient = inject(HttpClient);
  error = false;
  private router = inject(Router);

  onSubmit() {
    const username = this.registerForm.value.username;
    const password = this.registerForm.value.password;
    this.httpClient.post<UserCredential>("http://localhost:8081/auth/register", {username: username, password: password})
      .subscribe({
        complete: () => this.router.navigate(["auth/login"], {
          replaceUrl: true
        }),
        error: () => this.error = true
      })
  }
}
