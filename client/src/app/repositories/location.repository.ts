import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Filter} from '../models/filter.model';
import {FilteredLocations} from '../models/entity/filtered-response/filtered-locations.model';

@Injectable({
  providedIn: 'root'
})
export class LocationRepository {
  private api = `${environment.api}/location`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<FilteredLocations> {
    return this.http.post<FilteredLocations>(`${this.api}/filtered`, filter);
  }

  add(location: Location): Observable<void> {
    return this.http.post<void>(`${this.api}`, location);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, location: Location): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, location);
  }
}
