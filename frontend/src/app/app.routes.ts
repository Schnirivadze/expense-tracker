import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AddTransactionComponent } from './components/add-transaction/add-transaction.component';

export const routes: Routes = [
	{ path: '', pathMatch: 'full', redirectTo:'login' },
	{ path: 'login', component: LoginComponent },
	{ path: 'register', component: RegisterComponent },
	{ path: 'dashboard', component: DashboardComponent },
	{ path: 'add', component: AddTransactionComponent },
	{ path: '**', component: PageNotFoundComponent }
];
