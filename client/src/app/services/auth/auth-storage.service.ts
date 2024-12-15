import { Injectable } from '@angular/core';
import {JwtModel} from '../../models/auth/jwt.model';

@Injectable({
  providedIn: 'root'
})
export class AuthStorageService {

  private readonly TOKEN_KEY = 'jwt_token';
  private readonly LOGIN_KEY = 'login';

  constructor() { }

  reset(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.LOGIN_KEY);
  }

  setToken(jwt: JwtModel): void {
    if (jwt.token != null) {
      localStorage.setItem(this.TOKEN_KEY, jwt.token);
    }
  }

  getToken(): JwtModel {
    const token = localStorage.getItem(this.TOKEN_KEY);
    return {
      token: token === null ? undefined : token
    };
  }

  setLogin(login: string): void {
    localStorage.setItem(this.LOGIN_KEY, login);
  }

  getLogin(): string | null {
    return localStorage.getItem(this.LOGIN_KEY);
  }
}
