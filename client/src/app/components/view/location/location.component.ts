import { Component } from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {Button} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {PrimeTemplate} from 'primeng/api';
import {TableModule} from 'primeng/table';
import {environment} from '../../../environment/environment';
import {LocationRepository} from '../../../repositories/location.repository';
import {
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';

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

  constructor(protected locationRepo: LocationRepository) {}
}
