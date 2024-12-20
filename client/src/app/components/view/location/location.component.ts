import { Component } from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {Button} from 'primeng/button';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {ConfirmationService, PrimeTemplate} from 'primeng/api';
import {TableModule} from 'primeng/table';
import {environment} from '../../../environment/environment';
import {LocationRepository} from '../../../repositories/location.repository';
import {
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';
import {Location} from '../../../models/entity/location.model';
import {Filter} from '../../../models/filter.model';
import {FilteredResponse} from '../../../models/entity/filtered-response';
import {ToastService} from '../../../services/toast.service';
import {LocationFormComponent} from '../../location-form/location-form.component';
import {CoordinatesFormComponent} from '../../coordinates-form/coordinates-form.component';
import {LocationForm} from '../../../models/forms/location.form';
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
    ServerSideEntityTableComponent,
    LocationFormComponent,
    CoordinatesFormComponent
  ],
  templateUrl: './location.component.html'
})
export class LocationComponent {
  protected readonly TABLE_CONFIG: TableConfig = {
    pageSize: environment.tableDefPageSize,
    columns: ['id', 'name', 'x', 'y']
  };

  data!: Location[];
  count!: number;
  lastTableFilters!: Filter;

  form = {
    visible: false,
    location: new LocationForm(),
    id: 0
  };

  constructor(
    private locationRepo: LocationRepository,
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
        this.locationRepo.delete(id).subscribe(() => {
          this.loadData(this.lastTableFilters);
          this.toast.success('Success', 'Coordinates was deleted');
        });
      }
    });
  }

  add(): void {
    this.form.location = new LocationForm();
    this.form.id = 0;
    this.form.visible = true;
  }

  onFormSave(location: LocationForm): void {
    if (this.form.id) {
      this.locationRepo.update(this.form.id, location)
        .subscribe(() => this.successSubscribe(false));
    } else {
      this.locationRepo.add(location)
        .subscribe(() => this.successSubscribe(true));
    }
  }

  successSubscribe(isNew: boolean): void {
    this.toast.success('Success', 'Location was ' + isNew ? 'added' : 'updated');
    this.loadData(this.lastTableFilters);
  }

  edit(location: Entity): void {
    this.form.location = new LocationForm(<Location>location);
    this.form.id = location.id;
    this.form.visible = true;
  }

  loadData(filter: Filter): void {
    this.lastTableFilters = filter;
    this.locationRepo.getFiltered(filter).subscribe((resp: FilteredResponse) => {
      this.data = <Location[]>resp.data;
      this.count = resp.count;
    });
  }
}
