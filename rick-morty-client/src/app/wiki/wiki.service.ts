import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { PageModel } from './page.model';

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

  fetchDataWithPages<T>(url: string, pageNumber: number = 0): Observable<PageModel<T>> {
    return this.httpClient.get<PageModel<T>>(`${url}?page=${pageNumber}`).pipe(
      map(data => data),
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

  putData<T>(body: object, url: string): Observable<T> {
    return this.httpClient.put<T>(url, body, { withCredentials: true }).pipe(
      catchError((error) => {
        throw error;
      })
    )
  }

  deleteData(url: string): Observable<void> {
    const apiUrl = url.replace("http://localhost:4200/wiki", "http://localhost:8081/api");
    return this.httpClient.delete<void>(apiUrl, { withCredentials: true }).pipe(
      catchError((error) => {
        throw error;
      })
    )
  }

  navigateTo(...args: string[]) {
    this.router.navigate(['wiki', ...args]);
  }

  goToResourceNotFound() {
    this.router.navigate(["**"]);
  }
}
