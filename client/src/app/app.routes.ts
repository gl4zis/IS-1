import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {AuthGuard} from './interceptors/auth.guard';
import {AuthorizationComponent} from './components/view/authorization/authorization.component';
import {PersonComponent} from './components/view/person/person.component';
import {AdminComponent} from './components/view/admin/admin.component';
import {LocationComponent} from './components/view/location/location.component';
import {CoordinatesComponent} from './components/view/coordinates/coordinates.component';
import {SpecialComponent} from './components/view/special/special.component';
import {ImportComponent} from './components/view/import/import.component';

export const routes: Routes = [
  { path: 'auth', component: AuthorizationComponent },
  { path: 'person', component: PersonComponent, canActivate: [AuthGuard] },
  { path: 'location', component: LocationComponent, canActivate: [AuthGuard] },
  { path: 'coordinates', component: CoordinatesComponent, canActivate: [AuthGuard] },
  { path: 'special', component: SpecialComponent, canActivate: [AuthGuard] },
  { path: 'import', component: ImportComponent, canActivate: [AuthGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: "/person", pathMatch: "full" },
  { path: '**', redirectTo: "/person" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutes { }
