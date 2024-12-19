import {Component, Input, OnInit} from '@angular/core';
import {Button} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {NavHeaderComponent} from '../nav-header/nav-header.component';
import {PrimeTemplate} from 'primeng/api';
import {TableLazyLoadEvent, TableModule} from 'primeng/table';
import {NgForOf, NgIf} from '@angular/common';
import {Filter, SortType} from '../../models/filter.model';
import {Observable} from 'rxjs';
import {environment} from '../../environment/environment';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastService} from '../../services/toast.service';
import {Entity} from '../../models/entity/entity.model';

export interface TableConfig {
  pageSize: number;
  columns: string[];
}

export interface DataHolder {
  data: Entity[];
  count: number;
}

export interface DataSupplier {
  getFiltered: (f: Filter) => Observable<DataHolder>
}

@Component({
  selector: 'entity-table',
  standalone: true,
  imports: [
    Button,
    FormsModule,
    InputTextModule,
    NavHeaderComponent,
    PrimeTemplate,
    TableModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './server-side-entity-table.component.html',
  styleUrl: './server-side-entity-table.component.css'
})
export class ServerSideEntityTableComponent implements OnInit {
  @Input() tableConfig!: TableConfig;
  @Input() dataSupplier!: DataSupplier;

  data: Entity[] = [];
  count: number = 0;

  nextRefreshWaitIdx?: any;

  filter: Filter = {
    paginator: {
      page: 1,
      size: 10
    },
    sorter: {
      field: 'id',
      type: SortType.ASC
    },
    filters: {}
  };

  constructor(private toast: ToastService) {
  }

  ngOnInit() {
    this.filter.paginator.size = this.tableConfig.pageSize;
    this.filter.filters = this.tableConfig.columns
      .reduce((acc: any, key: string) => {
        acc[key] = '';
        return acc;
      }, {});
  }

  updateData($event?: TableLazyLoadEvent): void {
    if (this.nextRefreshWaitIdx) {
      clearTimeout(this.nextRefreshWaitIdx);
      this.nextRefreshWaitIdx = undefined;
    }
    if ($event) {
      this.filter.paginator.page = ($event.first || 0) / this.tableConfig.pageSize + 1;
      this.filter.sorter.field = <string>$event.sortField || 'id';
      this.filter.sorter.type = ($event.sortOrder || 1) === 1 ? SortType.ASC : SortType.DESC;
    }
    this.nextRefreshWaitIdx = setTimeout(() => this.updateData(), environment.dataRefreshInterval);
    this.loadData();
  }

  loadData(): void {
    this.dataSupplier.getFiltered(this.filter).subscribe({
      next: (holder: DataHolder) => {
        this.data = holder.data;
        this.count = holder.count;
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
