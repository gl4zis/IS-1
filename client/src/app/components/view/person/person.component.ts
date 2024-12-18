import { Component } from '@angular/core';
import {NavHeaderComponent} from "../../nav-header/nav-header.component";

@Component({
  selector: 'person-page',
  standalone: true,
    imports: [
        NavHeaderComponent
    ],
  templateUrl: './person.component.html'
})
export class PersonComponent {

}
