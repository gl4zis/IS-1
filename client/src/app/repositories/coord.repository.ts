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
export class CoordRepository implements DataSource {
  private api = `${environment.api}/coord`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<DataHolder> {
    return this.http.post<DataHolder>(`${this.api}/filtered`, filter);
  }

  add(coord: Entity): Observable<void> {
    return this.http.post<void>(`${this.api}`, coord);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, coord: Entity): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, coord);
  }
}
