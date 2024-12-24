export enum ImportStatus {
  IN_PROGRESS = 'IN PROGRESS',
  COMPLETED = 'COMPLETED',
  FAILED = 'FAILED',
}

export interface ImportHistory {
  id: number;
  fileName: string;
  status: ImportStatus;
  insertedPeople?: number;
  insertedCoordinates?: number;
  insertedLocations?: number;
  created_by?: string;
  downloadKey?: string;
}

export interface ImportsResponse {
  data: ImportHistory[];
  count: number;
}
