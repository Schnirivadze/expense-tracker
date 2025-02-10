import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', component: DashboardComponent },
	{ path: '**', component: PageNotFoundComponent }
];
