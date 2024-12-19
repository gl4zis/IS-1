import {Coordinates} from '../coordinates.model';
import {Count} from './count.model';

export interface FilteredCoordinates extends Count {
  coordinates: Coordinates[];
}
