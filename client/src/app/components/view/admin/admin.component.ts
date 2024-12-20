import {Component, OnInit} from '@angular/core';
import {NavHeaderComponent} from '../../nav-header/nav-header.component';
import {AdminRepository} from '../../../repositories/admin.repository';
import {Observable} from 'rxjs';
import {NgForOf} from '@angular/common';
import {Button} from 'primeng/button';
import {CardModule} from 'primeng/card';

@Component({
  selector: 'admin-page',
  standalone: true,
  imports: [
    NavHeaderComponent,
    NgForOf,
    Button,
    CardModule
  ],
  templateUrl: './admin.component.html'
})
export class AdminComponent implements OnInit {
  bids: string[] = [];

  constructor(private adminRepo: AdminRepository) {}

  ngOnInit() {
    this.getBids();
  }

  getBids() {
    this.adminRepo.getBids().subscribe(bids => this.bids = bids);
  }

  accept(login: string) {
    this.adminRepo.accept(login).subscribe(() => this.getBids());
  }

  reject(login: string) {
    this.adminRepo.reject(login).subscribe(() => this.getBids());
  }
}
