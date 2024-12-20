import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environment/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminRepository {
  private api = `${environment.api}/admin`;

  constructor(private http: HttpClient) {}

  getBids(): Observable<string[]> {
    return this.http.get<string[]>(`${this.api}/bid`);
  }

  accept(login: string): Observable<void> {
    return this.http.post<void>(`${this.api}/bid/accept?login=${login}`, {});
  }

  reject(login: string): Observable<void> {
    return this.http.delete<void>(`${this.api}/bid/reject?login=${login}`);
  }
}
