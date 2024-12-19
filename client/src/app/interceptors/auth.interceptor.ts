import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthStorageService} from '../services/auth/auth-storage.service';
import {catchError, Observable} from 'rxjs';
import {ToastService} from '../services/toast.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private authStorage: AuthStorageService,
    private toast: ToastService,
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authStorage.getToken();
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        switch (error.status) {
          case 401: {
            this.authStorage.reset();
            this.toast.warn('Oops', 'Looks like you are unauthorized. Try to sign in');
            break;
          }
          case 403: {
            this.toast.warn('Oops', 'Looks like you dont have permissions for this resource');
            break;
          }
        }

        throw error;
      })
    );
  }
}
