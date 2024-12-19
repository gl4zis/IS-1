import {Count} from './count.model';
import {Location} from '../location.model';

export interface FilteredLocations extends Count {
  locations: Location[];
}
