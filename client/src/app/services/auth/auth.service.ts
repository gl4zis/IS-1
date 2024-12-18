import {Injectable} from '@angular/core';
import {AuthStorageService} from './auth-storage.service';
import {AuthRepository} from '../../repositories/auth.repository';
import {LoginReq} from '../../models/auth/login.model';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {JwtModel} from '../../models/auth/jwt.model';
import {RegisterReq} from '../../models/auth/register.model';
import {ToastService} from '../toast.service';
import {firstValueFrom} from 'rxjs';
import {Router} from '@angular/router';

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
      next: (res: JwtModel) => {
        if (res.token != undefined) {
          this.setAuth(req.login, res);
        }
      },
      error: (error: HttpErrorResponse) => {
        this.resetAuth();
        this.toast.httpError(error);
      }
    });
  }

  register(req: RegisterReq) {
    this.repo.register(req).subscribe({
      next: (res: HttpResponse<JwtModel>) => {
        if (res.status === 201) {
          this.setAuth(req.login, res.body!);
        } else if (res.status === 202) {
          this.resetAuth();
          this.toast.info('Accepted', 'Your application in process');
        }
      },
      error: (error: HttpErrorResponse) => {
        this.resetAuth();
        this.toast.httpError(error);
      }
    });
  }

  logout(): void {
    this.resetAuth();
  }

  getLogin(): string | null {
    return this.storage.getLogin();
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

  private setAuth(login: string, jwt: JwtModel): void {
    this.storage.setLogin(login);
    this.storage.setToken(jwt);
    this.router.navigate(['/table']);
  }

  private resetAuth(): void {
    this.storage.reset();
    this.router.navigate(['/auth']);
  }
}
