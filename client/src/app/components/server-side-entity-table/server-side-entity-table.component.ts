import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Button} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {NavHeaderComponent} from '../nav-header/nav-header.component';
import {PrimeTemplate} from 'primeng/api';
import {TableLazyLoadEvent, TableModule} from 'primeng/table';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {Filter, SortType} from '../../models/filter.model';
import {environment} from '../../environment/environment';
import {Entity} from '../../models/entity/entity.model';
import {count} from 'rxjs';

export interface TableConfig {
  pageSize: number;
  columns: string[];
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
    NgIf,
    NgClass
  ],
  templateUrl: './server-side-entity-table.component.html',
  styleUrl: './server-side-entity-table.component.css'
})
export class ServerSideEntityTableComponent implements OnInit {
  @Input() tableConfig!: TableConfig;
  @Input() data!: any[];
  @Input() count!: number;
  @Input() addButtons: boolean = true;

  @Output() update: EventEmitter<any> = new EventEmitter();
  @Output() delete: EventEmitter<number> = new EventEmitter();
  @Output() needRefresh: EventEmitter<Filter> = new EventEmitter();

  nextRefreshWaitIdx?: any;
  filtersUpdateId?: any;

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
    }
    if ($event) {
      this.filter.paginator.page = ($event.first || 0) / this.tableConfig.pageSize + 1;
      this.filter.sorter.field = <string>$event.sortField || 'id';
      this.filter.sorter.type = ($event.sortOrder || 1) === 1 ? SortType.ASC : SortType.DESC;
    }
    this.needRefresh.emit(this.filter);
    this.nextRefreshWaitIdx = setTimeout(() => this.updateData(), environment.dataRefreshInterval);
  }

  filtersOnInput(): void {
    if (this.filtersUpdateId) {
      clearTimeout(this.filtersUpdateId);
    }

    this.filtersUpdateId = setTimeout(() => {
      this.filter.paginator.page = 1;
      this.updateData();
    }, 500);
  }

  getFirstRowIndex(): number {
    if (this.count == undefined) return 0;
    return (this.filter.paginator.page - 1) * this.filter.paginator.size;
  }
}
