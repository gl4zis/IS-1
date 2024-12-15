import {RouterModule, Routes} from '@angular/router';
import {TableComponent} from './components/table/table.component';
import {SpecialComponent} from './components/special/special.component';
import {NgModule} from '@angular/core';
import {AuthComponent} from './components/auth/auth.component';
import {AuthGuard} from './interceptors/auth.guard';

export const routes: Routes = [
  { path: 'auth', component: AuthComponent },
  { path: 'table', component: TableComponent, canActivate: [AuthGuard] },
  { path: 'special', component: SpecialComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: "/auth", pathMatch: "full" },
  { path: '**', redirectTo: "/auth" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutes { }
