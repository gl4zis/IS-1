import { Injectable } from '@angular/core';
import {Role} from '../../models/auth/role.model';

@Injectable({
  providedIn: 'root'
})
export class AuthStorageService {

  private readonly TOKEN_KEY = 'jwt_token';
  private readonly LOGIN_KEY = 'login';
  private readonly ROLE_KEY = 'role';

  constructor() { }

  reset(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.LOGIN_KEY);
    localStorage.removeItem(this.ROLE_KEY);
  }

  setToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | undefined {
    return this.getItem(this.TOKEN_KEY);
  }

  setLogin(login: string): void {
    localStorage.setItem(this.LOGIN_KEY, login);
  }

  getLogin(): string | undefined {
    return this.getItem(this.LOGIN_KEY)
  }

  setRole(role: Role): void {
    localStorage.setItem(this.ROLE_KEY, role);
  }

  isAdmin(): boolean {
    return localStorage.getItem(this.ROLE_KEY) === Role.ADMIN;
  }

  hasToken(): boolean {
    return this.getToken() != null;
  }

  private getItem(key: string): string | undefined {
    const item = localStorage.getItem(key);
    return item ? item : undefined;
  }
}
