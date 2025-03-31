import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserModel } from './user.model';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = "http://localhost:8081/auth";

  constructor(private httpClient: HttpClient, private router: Router, private cookieService: CookieService) {
  }

  registerUser(user: UserModel): Observable<UserModel> {
    return this.httpClient.post<UserModel>(`${this.apiUrl}/register`, user)
      .pipe(
        tap(() => this.router.navigate(["auth/login"], {replaceUrl: true})),
        catchError(error => {
          return throwError(() => error);
        })
      );
  }

  loginUser(user: UserModel): Observable<any> {
    return this.httpClient.post<void>(`${this.apiUrl}/login`, user, { withCredentials: true })
      .pipe(
        catchError(error => {
          return throwError(() => error);
        })
      );
  }

  logout(): void {
    this.cookieService.delete('jwt', '/');
    this.router.navigate(["auth/login"]);
  }
}
