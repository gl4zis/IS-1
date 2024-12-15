import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {ToastModule} from 'primeng/toast';
import {CommonModule} from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, ToastModule],
  templateUrl: './app.component.html',
})
export class AppComponent {

}
