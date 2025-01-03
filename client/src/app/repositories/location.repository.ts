import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Filter} from '../models/filter.model';
import {FilteredResponse} from '../models/entity/filtered-response';
import {LocationForm} from '../models/forms/location.form';
import {Selected} from '../models/util/selected.model';

@Injectable({
  providedIn: 'root'
})
export class LocationRepository {
  private api = `${environment.api}/location`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<FilteredResponse> {
    return this.http.post<FilteredResponse>(`${this.api}/filtered`, filter);
  }

  add(location: LocationForm): Observable<void> {
    return this.http.post<void>(`${this.api}`, location);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, location: LocationForm): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, location);
  }

  getSelected(): Observable<Selected[]> {
    return this.http.get<Selected[]>(`${this.api}/select`);
  }

  getLinkedPeople(locationId: number): Observable<Selected[]> {
    return this.http.get<Selected[]>(`${this.api}/${locationId}/linked-people`);
  }

  isNameUnique(name: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.api}/check-name-unique?name=${name}`);
  }
}
