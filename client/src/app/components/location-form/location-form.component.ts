import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FloatLabelModule} from 'primeng/floatlabel';
import {DialogModule} from 'primeng/dialog';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {LocationForm} from '../../models/forms/location.form';
import {NgIf} from '@angular/common';
import {CheckboxModule} from 'primeng/checkbox';
import {Button} from 'primeng/button';

@Component({
  selector: 'location-form',
  standalone: true,
  imports: [
    FloatLabelModule,
    DialogModule,
    FormsModule,
    InputTextModule,
    NgIf,
    CheckboxModule,
    Button
  ],
  templateUrl: './location-form.component.html'
})
export class LocationFormComponent {
  @Input() visible!: boolean;
  @Input() location!: LocationForm;
  @Input() isNew!: boolean;

  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();
  @Output() save: EventEmitter<LocationForm> = new EventEmitter();

  onClose() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

  onSave() {
    this.onClose();
    this.save.emit(this.location);
  }

  protected readonly Number = Number;
}
