import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthStorageService} from '../services/auth/auth-storage.service';
import {Observable} from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authStorage: AuthStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwt = this.authStorage.getToken();
    if (jwt.token) {
      const cloned = req.clone({
        setHeaders: {
          Authorization: `Bearer ${jwt.token}`
        }
      });
      return next.handle(cloned);
    }
    return next.handle(req);
  }
}
