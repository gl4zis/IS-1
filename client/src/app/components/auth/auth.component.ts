import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {Router} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CheckboxModule} from 'primeng/checkbox';
import {CardModule} from 'primeng/card';
import {NgIf} from '@angular/common';
import {Button} from 'primeng/button';
import {LoginReq} from '../../models/auth/login.model';
import {InputTextModule} from 'primeng/inputtext';
import {FloatLabelModule} from 'primeng/floatlabel';
import {PasswordModule} from 'primeng/password';
import {Role} from '../../models/auth/role.model';
import {ToggleButtonModule} from 'primeng/togglebutton';

@Component({
  selector: 'app-auth',
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
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {
  form: LoginReq;
  isSignIn = true;
  isAdmin = false;

  isUsernameValid = true;
  isPasswordValid = true;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
    this.form = {
      login: '',
      password: ''
    }
  }

  ngOnInit(): void {
    this.authService.isAuthenticated$.subscribe(isAuth => {
      if (isAuth) this.router.navigate(['/table']);
      else this.router.navigate(['/auth']);
    })
  }

  onSubmit(): void {
    this.validatePassword();
    this.validateUsername();
    if (!this.isFormValid()) return;
    if (this.isSignIn) {
      this.authService.login(this.form);
    } else {
      this.authService.register({
        ...this.form,
        role: this.isAdmin ? Role.ADMIN : Role.USER
      });
    }
  }

  validateUsername(): void {
    this.isUsernameValid = this.form.login.length >= 4;
  }

  validatePassword(): void {
    this.isPasswordValid = this.form.password.length >= 6;
  }

  isFormValid(): boolean {
    return this.isUsernameValid && this.isPasswordValid;
  }
}
