import {Component, OnInit} from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {TableModule} from 'primeng/table';
import {Coordinates} from '../../../models/entity/coordinates.model';
import {CoordRepository} from '../../../repositories/coord.repository';
import {ToastService} from '../../../services/toast.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'coordinates-page',
  standalone: true,
  imports: [
    NavHeaderComponent,
    TableModule
  ],
  templateUrl: './coordinates.component.html'
})
export class CoordinatesComponent implements OnInit {
  coordinates: Coordinates[] = [];

  constructor(
    private coordRepo: CoordRepository,
    private toast: ToastService
  ) {}

  ngOnInit() {
    this.loadData();
  }

  loadData(): void {
    this.coordRepo.getAll().subscribe({
      next: (data: Coordinates[]) => this.coordinates = data,
      error: (error: HttpErrorResponse) => this.toast.httpError(error)
    });
  }
}
