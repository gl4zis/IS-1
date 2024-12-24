import { Component } from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {FileRepository} from '../../../repositories/file.repository';
import {ToastService} from '../../../services/toast.service';
import {FileUploadModule} from 'primeng/fileupload';
import {environment} from '../../../environment/environment';
import {
  ServerSideEntityTableComponent,
  TableConfig
} from '../../server-side-entity-table/server-side-entity-table.component';
import {Filter} from '../../../models/filter.model';
import {FilteredResponse} from '../../../models/entity/filtered-response';
import {Coordinates} from '../../../models/entity/coordinates.model';
import {ImportHistory, ImportsResponse} from '../../../models/import-history.model';

export const DEF_FILE_NAME = 'untitled';

@Component({
  selector: 'file-import',
  standalone: true,
  imports: [
    NavHeaderComponent,
    FileUploadModule,
    ServerSideEntityTableComponent
  ],
  templateUrl: './import.component.html'
})
export class ImportComponent {
  fileName: string = DEF_FILE_NAME;
  protected readonly TABLE_CONFIG: TableConfig = {
    pageSize: environment.tableDefPageSize,
    columns: ['id', 'file', 'status', 'insertedPeople', 'insertedCoordinates', 'insertedLocations', 'createdBy']
  };

  data!: ImportHistory[];
  count!: number;
  lastTableFilters!: Filter;

  constructor(
    private fileRepo: FileRepository,
    private toast: ToastService,
  ) {}

  onFileSelect(event: any): void {
    this.fileName = event.files[0].name;
  }

  onFileUpload(): void {
    this.loadData(this.lastTableFilters);
    this.toast.success("Success!", `File ${this.fileName} successfully uploaded!`);
  }

  uploadLink(): string {
    return this.fileRepo.createUploadLink(this.fileName);
  }

  loadData(filter: Filter): void {
    filter.filters.fileName = filter.filters.file;
    delete filter.filters.file;
    this.lastTableFilters = filter;

    this.fileRepo.getHistory(filter).subscribe((resp: ImportsResponse) => {
      this.data = resp.data.map(history => {
        const link = history.downloadKey ? this.fileRepo.createDownloadLink(history.downloadKey) : null;
        return {
          ...history,
          file: link != null ? `<a href="${link}" download>${history.fileName}</a>` : history.fileName,
          accessible: true
        }
      });
      this.count = resp.count;
    });
  }
}
