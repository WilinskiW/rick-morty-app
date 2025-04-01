import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class WikiService {
  private httpClient = inject(HttpClient);
  router = inject(Router);

  fetchData<T>(url: string): Observable<T> {
    return this.httpClient.get<T>(url).pipe(
      catchError((error) => {
        throw error;
      })
    )
  }

  sendData<T>(body: object, url: string): Observable<T> {
    return this.httpClient.post<T>(url, body, { withCredentials: true }).pipe(
      catchError((error) => {
        throw error;
      })
    )
  }

  patchData<T>(body: object, url: string): Observable<T> {
    return this.httpClient.put<T>(url, body, { withCredentials: true }).pipe(
      catchError((error) => {
        throw error;
      })
    )
  }

  deleteData(url: string): Observable<void> {
    console.log(url);
    const apiUrl = url.replace("http://localhost:4200/wiki", "http://localhost:8081/api");
    console.log(apiUrl);
    return this.httpClient.delete<void>(apiUrl, { withCredentials: true }).pipe(
      catchError((error) => {
        throw error;
      })
    )
  }

  navigateTo(...args: string[]) {
    this.router.navigate(['wiki', ...args]);
  }
}
