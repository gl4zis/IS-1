import {Entity} from './entity.model';

export interface Location extends Entity {
  name: string;
  x: number;
  y: number;
}
