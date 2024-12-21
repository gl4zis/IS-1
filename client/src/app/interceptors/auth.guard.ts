import {Injectable} from '@angular/core';
import {Location} from '@angular/common';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
} from '@angular/router';
import {AuthService} from '../services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private router: Router,
    private location: Location,
  ) {}

  async canActivate(route: ActivatedRouteSnapshot): Promise<boolean> {
    if (route.url[0].path === 'admin' && !this.authService.isAdmin()) {
      this.location.back();
      return false;
    }

    if (await this.authService.checkAuth()) {
      return true;
    } else {
      this.router.navigate(['/auth']);
      return false;
    }
  }
}
