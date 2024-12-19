import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Filter} from '../models/filter.model';
import {DataHolder, DataSource} from './dataSource';
import {Entity} from '../models/entity.model';

@Injectable({
  providedIn: 'root'
})
export class LocationRepository implements DataSource {
  private api = `${environment.api}/location`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<DataHolder> {
    return this.http.post<DataHolder>(`${this.api}/filtered`, filter);
  }

  add(location: Entity): Observable<void> {
    return this.http.post<void>(`${this.api}`, location);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, location: Entity): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, location);
  }
}
