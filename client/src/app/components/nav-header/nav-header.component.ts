import {Component, OnInit} from '@angular/core';
import {MenubarModule} from 'primeng/menubar';
import {MenuItem} from 'primeng/api';
import {AuthService} from '../../services/auth/auth.service';
import {AuthStorageService} from '../../services/auth/auth-storage.service';
import {ProfileButtonComponent} from '../profile-button/profile-button.component';
import {Router} from '@angular/router';

@Component({
  selector: 'nav-header',
  standalone: true,
  imports: [
    MenubarModule,
    ProfileButtonComponent
  ],
  templateUrl: './nav-header.component.html'
})
export class NavHeaderComponent implements OnInit {
  pages?: MenuItem[];

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.pages = [
      {
        label: 'Person',
        command: () => this.router.navigate(['person']),
        icon: 'pi pi-face-smile'
      },
      {
        label: 'Location',
        command: () => this.router.navigate(['location']),
        icon: 'pi pi-globe'
      },
      {
        label: 'Coordinates',
        command: () => this.router.navigate(['coordinates']),
        icon: 'pi pi-compass'
      },
      {
        label: 'Special',
        command: () => this.router.navigate(['special']),
        icon: 'pi pi-lightbulb'
      },
      {
        label: 'Import',
        command: () => this.router.navigate(['import']),
        icon: 'pi pi-file-import'
      }
    ];
    if (this.authService.isAdmin()) {
      this.pages.push({
        label: 'Admin',
        command: () => this.router.navigate(['admin']),
        icon: 'pi pi-lock'
      });
    }
  }
}
