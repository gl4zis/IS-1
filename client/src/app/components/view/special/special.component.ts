import {Component, OnInit} from '@angular/core';
import {NavHeaderComponent} from "../../nav-header/nav-header.component";
import {ChipsModule} from 'primeng/chips';
import {FormsModule} from '@angular/forms';
import {Button} from 'primeng/button';
import {PersonRepository} from '../../../repositories/person.repository';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {DropdownModule} from 'primeng/dropdown';
import {Color} from '../../../models/entity/person.model';

@Component({
  selector: 'special-page',
  standalone: true,
  imports: [
    NavHeaderComponent,
    ChipsModule,
    FormsModule,
    Button,
    InputTextareaModule,
    DropdownModule
  ],
  templateUrl: './special.component.html'
})
export class SpecialComponent implements OnInit {
  heightSum: number = 0;
  maxPerson: string = '{}';

  weight: number = 75;
  countByWeight: number = 0;

  eyeColorForCount: Color = Color.GREEN;
  countByEyeColor: number = 0;

  eyeColorForProportion: Color = Color.BLUE;
  proportionByEyeColor: number = 0;

  colorOptions: { id: string; name: string }[] = Object.values(Color).map(c => ({ id: c, name: c }));

  constructor(private personRepo: PersonRepository) {}

  ngOnInit() {
    this.calcHeightSum();
    this.calcMaxPerson();
    this.calcCountByWeight();
    this.calcCountByEyeColor();
    this.calcProportionByEyeColor();
  }

  calcHeightSum() {
    this.personRepo.heightSum().subscribe(sum => this.heightSum = sum);
  }

  calcMaxPerson() {
    this.personRepo.maxPerson().subscribe(p => this.maxPerson = JSON.stringify(p, null, 2));
  }

  calcCountByWeight() {
    this.personRepo.countByWeight(this.weight).subscribe(count => this.countByWeight = count);
  }

  calcCountByEyeColor() {
    this.personRepo.countByEyeColor(this.eyeColorForCount).subscribe(count => this.countByEyeColor = count);
  }

  calcProportionByEyeColor() {
    this.personRepo.proportionByEyeColor(this.eyeColorForProportion).subscribe(prop => this.proportionByEyeColor = prop);
  }
}
