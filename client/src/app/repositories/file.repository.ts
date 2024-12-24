import {Injectable} from "@angular/core";
import {environment} from "../environment/environment";
import {HttpClient} from "@angular/common/http";
import {Filter} from "../models/filter.model";
import {Observable} from "rxjs";
import {ImportHistory, ImportsResponse} from "../models/import-history.model";

@Injectable({
    providedIn: 'root'
})
export class FileRepository {
  readonly api = `${environment.api}/file`;

  constructor(private http: HttpClient) {}

  getHistory(f: Filter): Observable<ImportsResponse> {
      return this.http.post<ImportsResponse>(`${this.api}/history`, f);
  }

  createDownloadLink(key: string): string {
    return `${this.api}/download/${key}`;
  }

  createUploadLink(filename: string): string {
    return `${this.api}/upload?filename=${filename}`;
  }
}
