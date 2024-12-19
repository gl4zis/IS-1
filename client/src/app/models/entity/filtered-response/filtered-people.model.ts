import {Count} from './count.model';
import {Person} from '../person.model';

export interface FilteredPeople extends Count {
  people: Person[];
}
