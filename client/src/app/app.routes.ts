import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from './table/table.component';
import {SpecialComponent} from './special/special.component';
import {NgModule} from '@angular/core';

export const routes: Routes = [
  { path: 'table', component: TableComponent },
  { path: 'special', component: SpecialComponent },
  { path: '', redirectTo: "/table", pathMatch: "full" },
  { path: '**', redirectTo: "/table" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutes { }
