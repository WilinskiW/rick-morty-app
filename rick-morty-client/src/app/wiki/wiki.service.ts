import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WikiService {
  constructor(private httpClient: HttpClient) {
  }

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
}
