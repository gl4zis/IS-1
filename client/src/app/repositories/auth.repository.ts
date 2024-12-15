import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginReq} from '../models/auth/login.model';
import {JwtModel} from '../models/auth/jwt.model';
import {RegisterReq} from '../models/auth/register.model';

@Injectable({
  providedIn: 'root'
})
export class AuthRepository {
  private api = `${environment.api}/auth`;

  constructor(private http: HttpClient) {}

  check(): Observable<void> {
    return this.http.get<void>(`${this.api}/check`);
  }

  login(req: LoginReq): Observable<JwtModel> {
    return this.http.post<JwtModel>(`${this.api}/login`, req);
  }

  register(req: RegisterReq): Observable<HttpResponse<JwtModel>> {
    return this.http.post<JwtModel>(`${this.api}/register`, req, {
      observe: 'response'
    });
  }
}
