import { Component } from '@angular/core';
import {NavHeaderComponent} from "../../nav-header/nav-header.component";
import {
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';
import {environment} from '../../../environment/environment';
import {PersonRepository} from '../../../repositories/person.repository';

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

  constructor(protected personRepo: PersonRepository) {}
}
