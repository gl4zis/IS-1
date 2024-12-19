import { Component } from '@angular/core';
import {NavHeaderComponent} from "../../nav-header/nav-header.component";
import {
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';
import {environment} from '../../../environment/environment';
import {PersonRepository} from '../../../repositories/person.repository';
import {Location} from '../../../models/entity/location.model';
import {Filter} from '../../../models/filter.model';
import {LocationRepository} from '../../../repositories/location.repository';
import {ConfirmationService} from 'primeng/api';
import {ToastService} from '../../../services/toast.service';
import {HttpErrorResponse} from '@angular/common/http';
import {FilteredResponse} from '../../../models/entity/filtered-response';
import {Person} from '../../../models/entity/person.model';

@Component({
  selector: 'person-page',
  standalone: true,
  imports: [
    NavHeaderComponent,
    ServerSideEntityTableComponent
  ],
  templateUrl: './person.component.html'
})
export class PersonComponent {
  protected readonly TABLE_CONFIG: TableConfig = {
    pageSize: environment.tableDefPageSize,
    columns: ['id', 'name', 'eyeColor', 'hairColor', 'height',
      'weight', 'passportId', 'nationality', 'coordinatesId', 'locationId']
  }

  data!: Person[];
  count!: number;
  lastTableFilters!: Filter;

  constructor(
    private personRepo: PersonRepository,
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
        this.personRepo.delete(id).subscribe(() => {
          this.loadData(this.lastTableFilters);
          this.toast.success('Success', 'Coordinates was deleted');
        });
      }
    });
  }

  loadData(filter: Filter): void {
    this.lastTableFilters = filter;
    this.personRepo.getFiltered(filter).subscribe((resp: FilteredResponse) => {
      this.data = <Person[]>resp.data;
      this.count = resp.count;
    });
  }
}
