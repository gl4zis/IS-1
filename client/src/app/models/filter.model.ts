export interface Paginator {
  page: number;
  size: number;
}

export enum SortType {
  ASC = 'ASC',
  DESC = 'DESC',
}

export interface Sorter {
  field: string;
  type: SortType;
}

export interface Filter {
  paginator: Paginator;
  sorter: Sorter;
  filters: any;
}
