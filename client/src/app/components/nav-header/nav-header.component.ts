import {Component, OnInit} from '@angular/core';
import {MenubarModule} from 'primeng/menubar';
import {MenuItem} from 'primeng/api';
import {AuthService} from '../../services/auth/auth.service';
import {AuthStorageService} from '../../services/auth/auth-storage.service';
import {ProfileButtonComponent} from '../profile-button/profile-button.component';

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

  constructor(private authStorage: AuthStorageService) {}

  ngOnInit() {
    this.pages = [
      {
        label: 'Person',
        url: '/person',
        icon: 'pi pi-face-smile'
      },
      {
        label: 'Location',
        url: '/location',
        icon: 'pi pi-globe'
      },
      {
        label: 'Coordinates',
        url: '/coordinates',
        icon: 'pi pi-compass'
      },
      {
        label: 'Special',
        url: '/special',
        icon: 'pi pi-lightbulb'
      }
    ];
    if (this.authStorage.isAdmin()) {
      this.pages.push({
        label: 'Admin',
        url: '/admin',
        icon: 'pi pi-lock'
      });
    }
  }
}
