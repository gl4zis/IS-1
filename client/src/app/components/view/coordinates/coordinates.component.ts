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
import {
  DataSupplier,
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';
import {map} from 'rxjs';

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
  protected dataSupplier: DataSupplier;

  constructor(coordRepo: CoordRepository) {
    this.dataSupplier = {
      getFiltered: (f: Filter) => coordRepo.getFiltered(f).pipe(
        map((response: FilteredCoordinates) => {
          return {
            data: response.coordinates,
            count: response.count
          }
        })
      )
    };
  }
}
