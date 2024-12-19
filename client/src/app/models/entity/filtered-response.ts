import {Entity} from './entity.model';

export interface FilteredResponse {
  data: Entity[];
  count: number;
}
