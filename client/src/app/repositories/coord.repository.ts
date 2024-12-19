import {Injectable} from '@angular/core';
import {environment} from '../environment/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Coordinates} from '../models/entity/coordinates.model';

@Injectable({
  providedIn: 'root'
})
export class CoordRepository {
  private api = `${environment.api}/coord`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Coordinates[]> {
    return this.http.get<Coordinates[]>(`${this.api}`);
  }
}
