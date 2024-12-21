import {Component} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CheckboxModule} from 'primeng/checkbox';
import {CardModule} from 'primeng/card';
import {Location, NgIf} from '@angular/common';
import {Button} from 'primeng/button';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {PasswordModule} from 'primeng/password';
import {ToggleButtonModule} from 'primeng/togglebutton';
import {LoginReq} from '../../../models/auth/login.model';
import {RegisterReq} from '../../../models/auth/register.model';
import {AuthService} from '../../../services/auth/auth.service';
import {Role} from '../../../models/auth/role.model';
import {AuthStorageService} from '../../../services/auth/auth-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'authorization-page',
  standalone: true,
  imports: [
    CheckboxModule,
    CardModule,
    ReactiveFormsModule,
    NgIf,
    Button,
    InputTextModule,
    FloatLabelModule,
    FormsModule,
    PasswordModule,
    ToggleButtonModule
  ],
  templateUrl: './authorization.component.html'
})
export class AuthorizationComponent {
  isSignIn = true;

  form: LoginReq | RegisterReq;
  isAdmin = false;

  constructor(
    protected authService: AuthService,
    protected authStorage: AuthStorageService,
    private router: Router
  ) {
    this.form = {
      login: '',
      password: ''
    }
  }

  onSubmit(): void {
    if (this.isSignIn) {
      this.authService.login(this.form);
    } else {
      this.authService.register({
        ...this.form,
        role: this.isAdmin ? Role.ADMIN : Role.USER
      });
    }
  }

  changeForm(): void {
    this.isSignIn = !this.isSignIn;
    this.form = {
      login: '',
      password: ''
    }
  }

  goToPerson(): void {
    this.router.navigate(['/person']);
  }
}
