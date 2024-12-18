import { Component } from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';

@Component({
  selector: 'location-page',
  standalone: true,
  imports: [
    NavHeaderComponent
  ],
  templateUrl: './location.component.html'
})
export class LocationComponent {

}
