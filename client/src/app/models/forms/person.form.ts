import {Color, Country, Person} from '../entity/person.model';

export class PersonForm {
  name: string = '';
  eyeColor: Color = Color.GREEN;
  hairColor: Color = Color.GREEN;
  height: number = 0;
  weight: number = 0;
  passportId: string = '';
  nationality?: Country;
  coordId?: number;
  locationId?: number;
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
