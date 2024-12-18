import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginReq} from '../models/auth/login.model';
import {UserResponse} from '../models/auth/user.response';
import {RegisterReq} from '../models/auth/register.model';
import {Role} from '../models/auth/role.model';

@Injectable({
  providedIn: 'root'
})
export class AuthRepository {
  private api = `${environment.api}/auth`;

  constructor(private http: HttpClient) {}

  check(): Observable<void> {
    return this.http.get<void>(`${this.api}/check`);
  }

  role(): Observable<Role> {
    return this.http.get<Role>(`${this.api}/role`);
  }

  login(req: LoginReq): Observable<UserResponse> {
    return this.http.post<UserResponse>(`${this.api}/login`, req);
  }

  register(req: RegisterReq): Observable<HttpResponse<UserResponse>> {
    return this.http.post<UserResponse>(`${this.api}/register`, req, {
      observe: 'response'
    });
  }
}
