import {Color, Person} from '../entity/person.model';

export const NULL_NATIONALITY = '[null]';

export class PersonForm {
  name: string = '';
  eyeColor: Color = Color.GREEN;
  hairColor: Color = Color.BLACK;
  height: number = 1;
  weight: number = 1;
  passportId: string = '';
  nationality?: string = NULL_NATIONALITY;
  coordId?: number = 0;
  locationId?: number = 0;
  adminAccess: boolean = false;

  constructor(e?: Person) {
    if (e) {
      this.name = e.name;
      this.eyeColor = e.eyeColor;
      this.hairColor = e.hairColor;
      this.height = e.height;
      this.weight = e.weight;
      this.passportId = e.passportId;
      this.nationality = e.nationality;
      this.coordId = e.coordinates.id;
      this.locationId = e.location ? e.location.id : undefined;
      this.adminAccess = e.adminAccess;
    }
  }
}
