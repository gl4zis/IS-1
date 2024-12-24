import { Component } from '@angular/core';
import {NavHeaderComponent} from "../../nav-header/nav-header.component";
import {
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';
import {environment} from '../../../environment/environment';
import {PersonRepository} from '../../../repositories/person.repository';
import {Filter} from '../../../models/filter.model';
import {ConfirmationService} from 'primeng/api';
import {ToastService} from '../../../services/toast.service';
import {FilteredResponse} from '../../../models/entity/filtered-response';
import {Person} from '../../../models/entity/person.model';
import {PersonFormComponent} from '../../person-form/person-form.component';
import {PersonForm} from '../../../models/forms/person.form';
import {Entity} from '../../../models/entity/entity.model';
import {Button} from 'primeng/button';

@Component({
  selector: 'person-page',
  standalone: true,
  imports: [
    NavHeaderComponent,
    ServerSideEntityTableComponent,
    PersonFormComponent,
    Button
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

  form = {
    visible: false,
    person: new PersonForm(),
    id: 0
  }

  constructor(
    private personRepo: PersonRepository,
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
        this.personRepo.delete(id).subscribe(() => {
          this.loadData(this.lastTableFilters);
          this.toast.success('Success', 'Coordinates was deleted');
        });
      }
    });
  }

  add(): void {
    this.form.person = new PersonForm();
    this.form.id = 0;
    this.form.visible = true;
  }

  onFormSave(person: PersonForm): void {
    if (this.form.id) {
      this.personRepo.update(this.form.id, person)
        .subscribe(() => this.successSubscribe(false));
    } else {
      this.personRepo.add(person)
        .subscribe(() => this.successSubscribe(true));
    }
  }

  successSubscribe(isNew: boolean): void {
    this.toast.success('Success', 'Person was ' + isNew ? 'added' : 'updated');
    this.loadData(this.lastTableFilters);
  }

  edit(person: Entity): void {
    this.form.person = new PersonForm(<Person>person);
    this.form.id = person.id;
    this.form.visible = true;
  }

  loadData(filter: Filter): void {
    this.lastTableFilters = filter;
    this.personRepo.getFiltered(filter).subscribe((resp: FilteredResponse) => {
      this.data = <Person[]>resp.data;
      this.count = resp.count;
    });
  }
}
