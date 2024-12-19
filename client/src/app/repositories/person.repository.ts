import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Filter} from '../models/filter.model';
import {map, Observable} from 'rxjs';
import {FilteredResponse} from '../models/entity/filtered-response';
import {Person} from '../models/entity/person.model';

@Injectable({
  providedIn: 'root'
})
export class PersonRepository {
  private api = `${environment.api}/person`;

  constructor(private http: HttpClient) {}

  getFiltered(filter: Filter): Observable<FilteredResponse> {
    return this.http.post<FilteredResponse>(`${this.api}/filtered`, filter).pipe(
      map((resp: FilteredResponse) => {
        return {
          data: resp.data.map((row: any) => { return { ...row, locationId: row.location!.id, coordinatesId: row.coordinates.id }; }),
          count: resp.count
        };
      }),
    );
  }

  add(person: Person): Observable<void> {
    return this.http.post<void>(`${this.api}`, person);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, location: Person): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, location);
  }
}
