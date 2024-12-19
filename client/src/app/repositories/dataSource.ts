import {Filter} from '../models/filter.model';
import {Observable} from 'rxjs';
import {Entity} from '../models/entity.model';

export interface DataHolder {
  data: Entity[];
  count: number;
}

export interface DataSource {
  getFiltered: (f: Filter) => Observable<DataHolder>;
}
