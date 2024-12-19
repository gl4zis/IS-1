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
import {ConfirmationService} from 'primeng/api';
import {ToastService} from '../../../services/toast.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Filter} from '../../../models/filter.model';
import {Entity} from '../../../models/entity/entity.model';
import {FilteredResponse} from '../../../models/entity/filtered-response';
import {Coordinates} from '../../../models/entity/coordinates.model';

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

  data!: Coordinates[];
  count!: number;
  lastTableFilters!: Filter;

  constructor(
    private coordRepo: CoordRepository,
    private confirmation: ConfirmationService,
    private toast: ToastService,
  ) {}

  delete(id: number) {
    this.confirmation.confirm({
      header: 'Delete confirmation',
      message: 'Are you sure you want to delete this entity?',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass: "p-button-danger p-button-text",
      rejectButtonStyleClass: "p-button-text",
      accept: () => {
        this.coordRepo.delete(id).subscribe(() => {
          this.loadData(this.lastTableFilters);
          this.toast.success('Success', 'Coordinates was deleted');
        });
      }
    });
  }

  loadData(filter: Filter): void {
    this.lastTableFilters = filter;
    this.coordRepo.getFiltered(filter).subscribe((resp: FilteredResponse) => {
      this.data = <Coordinates[]>resp.data;
      this.count = resp.count;
    });
  }
}
