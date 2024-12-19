import { Component } from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {Button} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {PrimeTemplate} from 'primeng/api';
import {TableLazyLoadEvent, TableModule} from 'primeng/table';
import {Filter, SortType} from '../../../models/filter.model';
import {ToastService} from '../../../services/toast.service';
import {environment} from '../../../environment/environment';
import {HttpErrorResponse} from '@angular/common/http';
import {FilteredLocations} from '../../../models/entity/filtered-response/filtered-locations.model';
import {LocationRepository} from '../../../repositories/location.repository';
import {
  DataHolder,
  DataSupplier,
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';
import {map, max} from 'rxjs';
import {Entity} from '../../../models/entity/entity.model';

@Component({
  selector: 'location-page',
  standalone: true,
  imports: [
    NavHeaderComponent,
    Button,
    FormsModule,
    InputTextModule,
    PrimeTemplate,
    TableModule,
    ServerSideEntityTableComponent
  ],
  templateUrl: './location.component.html'
})
export class LocationComponent {
  protected readonly TABLE_CONFIG: TableConfig = {
    pageSize: environment.tableDefPageSize,
    columns: ['id', 'name', 'x', 'y']
  };
  protected dataSupplier: DataSupplier;

  constructor(locationRepo: LocationRepository) {
    this.dataSupplier = {
      getFiltered: (f: Filter) => locationRepo.getFiltered(f).pipe(
        map((response: FilteredLocations) => {
          return {
            data: response.locations,
            count: response.count
          };
        })
      )
    };
  }
}
