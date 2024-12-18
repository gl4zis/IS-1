import {Component, OnInit} from '@angular/core';
import {AuthStorageService} from '../../services/auth/auth-storage.service';
import {Button} from 'primeng/button';
import {IconFieldModule} from 'primeng/iconfield';
import {AvatarModule} from 'primeng/avatar';

@Component({
  selector: 'profile-button',
  standalone: true,
  imports: [
    Button,
    IconFieldModule,
    AvatarModule
  ],
  templateUrl: './profile-button.component.html'
})
export class ProfileButtonComponent implements OnInit {
  login?: string;

  constructor(private authStorage: AuthStorageService) {}

  ngOnInit() {
    this.login = this.authStorage.getLogin();
  }
}
