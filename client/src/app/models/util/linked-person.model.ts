import {Selected} from './selected.model';

export interface LinkedPerson extends Selected {
  coordId: number;
  locationId?: number;
  newId: number;
}
