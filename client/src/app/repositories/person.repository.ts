import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Filter} from '../models/filter.model';
import {map, Observable} from 'rxjs';
import {DataHolder, DataSource} from './dataSource';
import {Entity} from '../models/entity.model';

@Injectable({
  providedIn: 'root'
})
export class PersonRepository implements DataSource {
  private api = `${environment.api}/person`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<DataHolder> {
    return this.http.post<DataHolder>(`${this.api}/filtered`, filter).pipe(
      map((resp: DataHolder) => {
        return {
          data: resp.data.map((row: any) => { return { ...row, locationId: row.location!.id, coordinatesId: row.coordinates.id }; }),
          count: resp.count
        };
      }),
    );
  }

  add(person: Entity): Observable<void> {
    return this.http.post<void>(`${this.api}`, person);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, location: Entity): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, location);
  }
}
