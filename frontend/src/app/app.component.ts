import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';

@Component({
	selector: 'app-root',
	imports: [RouterOutlet, HeaderComponent],
	templateUrl: './app.component.html',
	styleUrl: './app.component.css'
})
export class AppComponent {
	title = 'frontend';
	showHeader = true;

	constructor(private router: Router) {
		this.router.events.subscribe(() => {
			this.showHeader = this.router.url !== '/login' && this.router.url !== '/register';
		});
	}

}
