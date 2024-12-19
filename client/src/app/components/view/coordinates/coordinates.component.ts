import {Component} from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {TableModule} from 'primeng/table';
import {CoordRepository} from '../../../repositories/coord.repository';
import {Button} from 'primeng/button';
import {InputTextModule} from 'primeng/inputtext';
import {FormsModule} from '@angular/forms';
import {StyleClassModule} from 'primeng/styleclass';
import {environment} from '../../../environment/environment';
import {IconFieldModule} from 'primeng/iconfield';
import {
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';

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
    IconFieldModule,
    ServerSideEntityTableComponent
  ],
  templateUrl: './coordinates.component.html'
})
export class CoordinatesComponent {
  protected readonly TABLE_CONFIG: TableConfig = {
    pageSize: environment.tableDefPageSize,
    columns: ['id', 'x', 'y']
  };

  constructor(protected coordRepo: CoordRepository) {}
}
