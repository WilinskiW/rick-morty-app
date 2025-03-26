import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserModel } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private httpClient: HttpClient, private router: Router) {
  }

  registerUser(user: UserModel) {
    let error = false;
    this.httpClient.post<UserModel>("http://localhost:8081/auth/register", user)
      .subscribe({
        complete: () => this.router.navigate(["auth/login"], {
          replaceUrl: true
        }),
        error: () => error = true
      })
    return error; // Return error for UI notifications
  }

  loginUser(user: UserModel) {
    let error = false;
    this.httpClient.post<UserModel>("http://localhost:8081/auth/login", user)
      .subscribe({
        complete: () => this.router.navigate(["/wiki/characters"], {
          replaceUrl: true
        }),
        error: () => error = true
      })
    return error; // Return error for UI notifications
  }
}
