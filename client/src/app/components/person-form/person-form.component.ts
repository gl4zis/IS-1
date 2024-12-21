import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Button} from 'primeng/button';
import {CheckboxModule} from 'primeng/checkbox';
import {DialogModule} from 'primeng/dialog';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {NgIf} from '@angular/common';
import {NULL_NATIONALITY, PersonForm} from '../../models/forms/person.form';
import {DropdownModule} from 'primeng/dropdown';
import {Color, Country} from '../../models/entity/person.model';
import {Selected} from '../../models/util/selected.model';
import {CoordRepository} from '../../repositories/coord.repository';
import {LocationRepository} from '../../repositories/location.repository';
import {PersonRepository} from '../../repositories/person.repository';

@Component({
  selector: 'person-form',
  standalone: true,
  imports: [
    Button,
    CheckboxModule,
    DialogModule,
    FloatLabelModule,
    FormsModule,
    InputTextModule,
    NgIf,
    DropdownModule
  ],
  templateUrl: './person-form.component.html'
})
export class PersonFormComponent implements OnInit {
  @Input() visible!: boolean;
  @Input() person!: PersonForm;
  @Input() isNew!: boolean;

  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();
  @Output() save: EventEmitter<PersonForm> = new EventEmitter();

  colorOptions: string[] = Object.values(Color);
  countryOptions: string[] = [NULL_NATIONALITY].concat(Object.values(Country));
  coordinatesOptions: Selected[] = [];
  locationOptions: Selected[] = [{ id: 0, name: '[null]' }];

  isNameUnique: boolean = true;
  isPassportUnique: boolean = true;

  isUnameUniqueReqId?: any;
  isPassportUniqueReqId?: any;

  constructor(
    private coordRepo: CoordRepository,
    private locationRepo: LocationRepository,
    private personRepo: PersonRepository,
  ) {}

  ngOnInit() {
    this.coordRepo.getSelected().subscribe(resp => this.coordinatesOptions = resp);
    this.locationRepo.getSelected().subscribe(resp => resp.forEach(l => this.locationOptions.push(l)));
  }

  onClose() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

  onSave() {
    if (this.person.nationality === NULL_NATIONALITY) {
      this.person.nationality = undefined;
    }
    if (this.person.locationId === 0) {
      this.person.locationId = undefined;
    }
    this.onClose();
    this.save.emit(this.person);
  }

  checkNameUniqueness(name: string): void {
    if (this.isUnameUniqueReqId) {
      clearTimeout(this.isUnameUniqueReqId);
    }

    this.isUnameUniqueReqId = setTimeout(
      () => this.personRepo.isNameUnique(name).subscribe((unique: boolean) => this.isNameUnique = unique),
      500
    );
  }

  checkPassportUniqueness(passportId: string): void {
    if (this.isPassportUniqueReqId) {
      clearTimeout(this.isPassportUniqueReqId);
    }

    this.isPassportUniqueReqId = setTimeout(
      () => this.personRepo.isPassportUnique(passportId).subscribe((unique: boolean) => this.isPassportUnique = unique),
      500
    );
  }

  protected readonly Math = Math;
  protected readonly Number = Number;
}
