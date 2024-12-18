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
        url: '/person'
      },
      {
        label: 'Location',
        url: '/location'
      },
      {
        label: 'Coordinates',
        url: '/coordinates'
      },
      {
        label: 'Special',
        url: '/special'
      }
    ];
    if (this.authStorage.isAdmin()) {
      this.pages.push({
        label: 'Admin',
        url: '/admin'
      });
    }
  }
}
