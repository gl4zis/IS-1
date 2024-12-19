import {Entity} from './entity.model';
import {Coordinates} from './coordinates.model';
import {Location} from './location.model';

export enum Color {
  GREEN = 'GREEN',
  BLUE = 'BLUE',
  BLACK = 'BLACK',
  WHITE = 'WHITE',
  ORANGE = 'ORANGE',
}

export enum Country {
  USA = 'USA',
  NORTH_KOREA = 'NORTH_KOREA',
  FRANCE = 'FRANCE',
  CHINA  = 'CHINA ',
}

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
