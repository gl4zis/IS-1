import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Filter} from '../models/filter.model';
import {FilteredResponse} from '../models/entity/filtered-response';
import {CoordForm} from '../models/forms/coord.form';
import {Selected} from '../models/util/selected.model';

@Injectable({
  providedIn: 'root'
})
export class CoordRepository {
  private api = `${environment.api}/coord`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<FilteredResponse> {
    return this.http.post<FilteredResponse>(`${this.api}/filtered`, filter);
  }

  add(coord: CoordForm): Observable<void> {
    return this.http.post<void>(`${this.api}`, coord);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, coord: CoordForm): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, coord);
  }

  getSelected(): Observable<Selected[]> {
    return this.http.get<Selected[]>(`${this.api}/select`);
  }

  getLinkedPeople(coordId: number): Observable<Selected[]> {
    return this.http.get<Selected[]>(`${this.api}/${coordId}/linked-people`);
  }
}
