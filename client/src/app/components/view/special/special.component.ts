import { Component } from '@angular/core';
import {NavHeaderComponent} from "../../nav-header/nav-header.component";

@Component({
  selector: 'special-page',
  standalone: true,
    imports: [
        NavHeaderComponent
    ],
  templateUrl: './special.component.html'
})
export class SpecialComponent {

}
