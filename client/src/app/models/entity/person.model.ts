import { Entity } from "./entity.model";
import {Color} from './color.model';
import {Country} from './country.model';
import {Coordinates} from './coordinates.model';
import {Location} from '@angular/common';

export interface Person extends Entity {
  name: string;
  eyeColor: Color;
  hairColor: Color;
  height: number;
  weight: number;
  passportId: string;
  nationality?: Country;
  coordinates: Coordinates;
  location?: Location;
}
