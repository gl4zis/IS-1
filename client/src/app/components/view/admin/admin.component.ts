import { Component } from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';

@Component({
  selector: 'admin-page',
  standalone: true,
  imports: [
    NavHeaderComponent
  ],
  templateUrl: './admin.component.html'
})
export class AdminComponent {

}
