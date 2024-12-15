import { Component } from '@angular/core';
import {Button} from 'primeng/button';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [
    Button
  ],
  templateUrl: './table.component.html',
})
export class TableComponent {
  onButtonClick() {
    alert('Button clicked!');
  }
}
