import {Coordinates} from '../entity/coordinates.model';

export class CoordForm {
  x: number = 0;
  y: number = 0;
  adminAccess: boolean = false;

  constructor(e?: Coordinates) {
    if (e) {
      this.x = e.x;
      this.y = e.y;
      this.adminAccess = e.adminAccess;
    }
  }
}
