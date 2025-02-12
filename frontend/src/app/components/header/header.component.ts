import { Component, inject, input } from '@angular/core';
import { ThemeService } from '../../services/theme.service';

@Component({
	selector: 'app-header',
	imports: [],
	templateUrl: './header.component.html',
})
export class HeaderComponent {
	private themeService = inject(ThemeService)
	toggleTheme(): void {
		this.themeService.setTheme((this.themeService.getTheme() == "light") ? "dark" : "light");
	}
	showAddButton = input<boolean>(true);
	title = input<string>("Header");
}
