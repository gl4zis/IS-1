import {Injectable, OnInit} from '@angular/core';
import {AuthStorageService} from './auth-storage.service';
import {AuthRepository} from '../../repositories/auth.repository';
import {LoginReq} from '../../models/auth/login.model';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {JwtModel} from '../../models/auth/jwt.model';
import {RegisterReq} from '../../models/auth/register.model';
import {ToastService} from '../toast.service';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authState: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public isAuthenticated$ = this.authState.asObservable();

  constructor(
    private storage: AuthStorageService,
    private repo: AuthRepository,
    private toast: ToastService,
  ) {
    this.repo.check().subscribe({
      next: () => this.authState.next(true),
      error: () => this.authState.next(false)
    });
  }

  login(req: LoginReq) {
    this.repo.login(req).subscribe({
      next: (res: JwtModel) => {
        console.log(res);
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

  isAuthenticated(): boolean {
    return this.authState.getValue();
  }

  private setAuth(login: string, jwt: JwtModel): void {
    this.authState.next(true);
    this.storage.setLogin(login);
    this.storage.setToken(jwt);
  }

  private resetAuth(): void {
    this.authState.next(false);
    this.storage.reset();
  }
}
