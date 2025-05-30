import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserCredentialModel } from './model/userCredential.model';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { TokenInfoModel } from './model/tokenInfo.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = "http://localhost:8081/auth";
  private userSubject = new BehaviorSubject<TokenInfoModel | null>(null);
  private httpClient = inject(HttpClient);
  private router = inject(Router);
  user$ = this.userSubject.asObservable();

  registerUser(user: UserCredentialModel): Observable<UserCredentialModel> {
    return this.httpClient.post<UserCredentialModel>(`${this.apiUrl}/register`, user)
      .pipe(
        tap(() => this.router.navigate(["auth/login"], {replaceUrl: true})),
        catchError(error => {
          return throwError(() => error);
        })
      );
  }

  loginUser(user: UserCredentialModel): Observable<any> {
    return this.httpClient.post<void>(`${this.apiUrl}/login`, user, {withCredentials: true})
      .pipe(
        catchError(error => {
          return throwError(() => error);
        })
      );
  }

  logout() {
    this.httpClient.post(`${this.apiUrl}/logout`, {}, {withCredentials: true}).subscribe({
      next: () => this.userSubject.next(null)
    })
    this.router.navigate(["auth/login"], {replaceUrl: true});
  }

  fetchCurrentUser(): Observable<TokenInfoModel | null> {
    return this.httpClient.get<TokenInfoModel>(`${this.apiUrl}/userInfo`, {withCredentials: true})
      .pipe(
        tap(user => this.userSubject.next(user)),
        catchError(() => {
          this.userSubject.next(null);
          return [null]; // musi być Observable z wartością
        })
      );
  }

  isAuthenticated() {
    return this.userSubject.value !== null;
  }
}
