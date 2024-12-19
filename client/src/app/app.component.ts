import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {ToastModule} from 'primeng/toast';
import {ConfirmDialogModule} from 'primeng/confirmdialog';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, ToastModule, ConfirmDialogModule],
  templateUrl: './app.component.html',
})
export class AppComponent {
}
