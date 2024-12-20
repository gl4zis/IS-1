import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CoordForm} from '../../models/forms/coord.form';
import {DialogModule} from 'primeng/dialog';
import {FloatLabelModule} from 'primeng/floatlabel';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';
import {InputTextModule} from 'primeng/inputtext';
import {Button} from 'primeng/button';
import {CheckboxModule} from 'primeng/checkbox';
import {LoginReq} from '../../models/auth/login.model';

@Component({
  selector: 'coordinates-form',
  standalone: true,
  imports: [
    DialogModule,
    InputTextModule,
    FloatLabelModule,
    FormsModule,
    NgIf,
    Button,
    CheckboxModule
  ],
  templateUrl: './coordinates-form.component.html'
})
export class CoordinatesFormComponent {
  @Input() visible!: boolean;
  @Input() coordinates!: CoordForm;
  @Input() isNew!: boolean;

  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();
  @Output() save: EventEmitter<CoordForm> = new EventEmitter();

  onClose() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

  onSave() {
    this.onClose();
    this.save.emit(this.coordinates);
  }

  protected readonly Number = Number;
}
