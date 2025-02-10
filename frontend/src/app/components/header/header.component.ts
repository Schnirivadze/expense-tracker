import { Component, inject } from '@angular/core';
import { ThemeService } from '../../services/theme.service';

@Component({
	selector: 'app-header',
	imports: [],
	templateUrl: './header.component.html',
	styleUrl: './header.component.css'
})
export class HeaderComponent {
	private themeService = inject(ThemeService)
	toggleTheme(): void {
		this.themeService.setTheme((this.themeService.getTheme() == "light") ? "dark" : "light");
	}
	closeDropdown(): void {
		const details = document.querySelector('details[open]');
		if (details) {
			details.removeAttribute('open');
		}
	}
}
