import {Count} from './count.model';

export interface FilteredLocations extends Count {
  locations: Location[];
}
