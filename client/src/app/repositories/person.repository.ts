import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Filter} from '../models/filter.model';
import {map, Observable} from 'rxjs';
import {FilteredResponse} from '../models/entity/filtered-response';
import {PersonForm} from '../models/forms/person.form';
import {Color, Person} from '../models/entity/person.model';

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

  add(person: PersonForm): Observable<void> {
    return this.http.post<void>(`${this.api}`, person);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  update(id: number, location: PersonForm): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, location);
  }

  heightSum(): Observable<number> {
    return this.http.get<number>(`${this.api}/height-sum`);
  }

  maxPerson(): Observable<Person> {
    return this.http.get<Person>(`${this.api}/max-by-coords`);
  }

  countByWeight(weight: number): Observable<number> {
    return this.http.get<number>(`${this.api}/count?weight=${weight}`);
  }

  countByEyeColor(eyeColor: Color): Observable<number> {
    return this.http.get<number>(`${this.api}/count?eyeColor=${eyeColor}`);
  }

  proportionByEyeColor(eyeColor: Color): Observable<number> {
    return this.http.get<number>(`${this.api}/proportion?eyeColor=${eyeColor}`);
  }
}
