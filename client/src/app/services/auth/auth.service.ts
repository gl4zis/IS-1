import {Injectable} from '@angular/core';
import {AuthStorageService} from './auth-storage.service';
import {AuthRepository} from '../../repositories/auth.repository';
import {LoginReq} from '../../models/auth/login.model';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {UserResponse} from '../../models/auth/user.response';
import {RegisterReq} from '../../models/auth/register.model';
import {ToastService} from '../toast.service';
import {firstValueFrom} from 'rxjs';
import {Router} from '@angular/router';
import {Role} from '../../models/auth/role.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private storage: AuthStorageService,
    private repo: AuthRepository,
    private toast: ToastService,
    private router: Router
  ) {
  }

  login(req: LoginReq) {
    this.repo.login(req).subscribe({
      next: (res: UserResponse) => {
        if (res.token != undefined) {
          this.setAuth(req.login, res);
        }
      },
      error: () => this.resetAuth()
    });
  }

  register(req: RegisterReq) {
    this.repo.register(req).subscribe({
      next: (res: HttpResponse<UserResponse>) => {
        if (res.status === 201) {
          this.setAuth(req.login, res.body!);
        } else if (res.status === 202) {
          this.resetAuth();
          this.toast.info('Accepted', 'Your application in process');
        }
      },
      error: () => this.resetAuth()
    });
  }

  logout(): void {
    this.resetAuth();
  }

  async checkAuth(): Promise<boolean> {
    try {
      await firstValueFrom(this.repo.check());
      return true;
    } catch (error) {
      return false;
    }
  }

  checkLogIn(): boolean {
    return this.storage.hasToken();
  }

  isAdmin(): boolean {
    return this.storage.getRole() === Role.ADMIN;
  }

  private setAuth(login: string, jwt: UserResponse): void {
    this.storage.setLogin(login);
    this.storage.setToken(jwt.token);
    this.storage.setRole(jwt.role);
    this.router.navigate(['table']);
  }

  private resetAuth(): void {
    this.storage.reset();
    this.router.navigate(['auth']);
  }
}
