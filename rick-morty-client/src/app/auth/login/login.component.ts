import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router, RouterLink } from '@angular/router';

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
  error = signal(false);
  private authService = inject(AuthService);
  private router = inject(Router);

  onSubmit() {
    const username = this.loginForm.value.username;
    const password = this.loginForm.value.password;

    if (!username || !password) {
      return;
    }

    this.authService.loginUser({username, password}).subscribe({
      complete: () => {
        this.authService.fetchCurrentUser().subscribe();
        this.router.navigate(["/wiki/characters"], { replaceUrl: true });
      },
      error: () => this.error.set(true)
    });
  }

  closeErrMsg() {
    this.error.set(false);
  }
}
