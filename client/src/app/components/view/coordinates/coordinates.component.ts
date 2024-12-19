import {Component} from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {TableLazyLoadEvent, TableModule} from 'primeng/table';
import {Coordinates} from '../../../models/entity/coordinates.model';
import {CoordRepository} from '../../../repositories/coord.repository';
import {ToastService} from '../../../services/toast.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Button} from 'primeng/button';
import {Filter, SortType} from '../../../models/filter.model';
import {InputTextModule} from 'primeng/inputtext';
import {FormsModule} from '@angular/forms';
import {StyleClassModule} from 'primeng/styleclass';
import {FilteredCoordinates} from '../../../models/entity/filtered-response/filtered-coordinates.model';
import {environment} from '../../../environment/environment';
import {IconFieldModule} from 'primeng/iconfield';

@Component({
  selector: 'coordinates-page',
  standalone: true,
  imports: [
    NavHeaderComponent,
    TableModule,
    Button,
    InputTextModule,
    FormsModule,
    StyleClassModule,
    IconFieldModule
  ],
  templateUrl: './coordinates.component.html'
})
export class CoordinatesComponent {
  protected readonly PAGE_SIZE = 10;
  protected readonly DEFAULT_FILTER: Filter = {
    paginator: {
      page: 1,
      size: this.PAGE_SIZE
    },
    sorter: {
      field: 'id',
      type: SortType.ASC
    },
    filters: {
      id: '',
      x: '',
      y: ''
    }
  };

  coordinates: Coordinates[] = [];
  count: number = 0;
  filter: Filter = this.DEFAULT_FILTER;

  nextRefreshWaitIdx?: any;

  constructor(
    private coordRepo: CoordRepository,
    private toast: ToastService
  ) {
  }

  updateData($event?: TableLazyLoadEvent): void {
    if (this.nextRefreshWaitIdx) {
      clearTimeout(this.nextRefreshWaitIdx);
      this.nextRefreshWaitIdx = undefined;
    }
    if ($event) {
      this.filter.paginator.page = ($event.first || 0) / this.PAGE_SIZE + 1;
      this.filter.sorter.field = <string>$event.sortField || 'id';
      this.filter.sorter.type = ($event.sortOrder || 1) === 1 ? SortType.ASC : SortType.DESC;
    }
    this.nextRefreshWaitIdx = setTimeout(() => this.updateData(), environment.dataRefreshInterval);
    this.loadData();
  }

  loadData(): void {
    this.coordRepo.getFiltered(this.filter).subscribe({
      next: (data: FilteredCoordinates) => {
        this.coordinates = data.coordinates;
        this.count = data.count;
      },
      error: (error: HttpErrorResponse) => this.toast.httpError(error)
    });
  }

  getFirstRowIndex(): number {
    const firstRowIdx = (this.filter.paginator.page - 1) * this.filter.paginator.size;
    if (firstRowIdx <= this.count) {
      return firstRowIdx;
    }
    this.filter.paginator.page = this.count / this.filter.paginator.size + 1;
    return (this.filter.paginator.page - 1) * this.filter.paginator.size;
  }
}
