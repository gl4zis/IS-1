import {Location} from '../entity/location.model';

export class LocationForm {
  name: string = '';
  x: number = 0;
  y: number = 0;
  adminAccess: boolean = false;

  constructor(e?: Location) {
    if (e) {
      this.name = e.name;
      this.x = e.x;
      this.y = e.y;
      this.adminAccess = e.adminAccess;
    }
  }
}
