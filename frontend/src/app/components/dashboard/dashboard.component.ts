import { CurrencyPipe, DatePipe, NgClass } from '@angular/common';
import { Component } from '@angular/core';

@Component({
	selector: 'app-dashboard',
	imports: [CurrencyPipe, NgClass, DatePipe],
	templateUrl: './dashboard.component.html',
	styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

}
