import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Coordinates} from '../models/entity/coordinates.model';
import {Filter} from '../models/filter.model';
import {FilteredCoordinates} from '../models/entity/filtered-response/filtered-coordinates.model';

@Injectable({
  providedIn: 'root'
})
export class CoordRepository {
  private api = `${environment.api}/coord`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<FilteredCoordinates> {
    return this.http.post<FilteredCoordinates>(`${this.api}/filtered`, filter);
  }

  add(coord: Coordinates): Observable<void> {
    return this.http.post<void>(`${this.api}`, coord);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, coord: Coordinates): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, coord);
  }
}
