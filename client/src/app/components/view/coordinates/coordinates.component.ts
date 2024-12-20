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
import {Filter} from '../../../models/filter.model';
import {FilteredResponse} from '../../../models/entity/filtered-response';
import {Coordinates} from '../../../models/entity/coordinates.model';
import {CoordinatesFormComponent} from '../../coordinates-form/coordinates-form.component';
import {CoordForm} from '../../../models/forms/coord.form';
import {Entity} from '../../../models/entity/entity.model';

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
    ServerSideEntityTableComponent,
    CoordinatesFormComponent
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

  form = {
    visible: false,
    coord: new CoordForm(),
    id: 0
  };

  constructor(
    private coordRepo: CoordRepository,
    private confirmation: ConfirmationService,
    private toast: ToastService,
  ) {}

  delete(id: number): void {
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

  add(): void {
    this.form.coord = new CoordForm();
    this.form.id = 0;
    this.form.visible = true;
  }

  onFormSave(coord: CoordForm): void {
    if (this.form.id) {
      this.coordRepo.update(this.form.id, coord)
        .subscribe(() => this.successSubscribe(false));
    } else {
      this.coordRepo.add(coord)
        .subscribe(() => this.successSubscribe(true));
    }
  }

  successSubscribe(isNew: boolean): void {
    this.toast.success('Success', 'Coordinates was ' + isNew ? 'added' : 'updated');
    this.loadData(this.lastTableFilters);
  }

  edit(coord: Entity): void {
    this.form.coord = new CoordForm(<Coordinates>coord);
    this.form.id = coord.id;
    this.form.visible = true;
  }

  loadData(filter: Filter): void {
    this.lastTableFilters = filter;
    this.coordRepo.getFiltered(filter).subscribe((resp: FilteredResponse) => {
      this.data = <Coordinates[]>resp.data;
      this.count = resp.count;
    });
  }
}
