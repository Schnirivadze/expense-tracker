import { Injectable } from '@angular/core';

@Injectable({
	providedIn: 'root'
})
export class ThemeService {

	constructor() { }
	
	// Method to set theme
	setTheme(theme: string): void {
		const html = document.documentElement;
		html.setAttribute('data-theme', theme); 
	}

	// Method to get current theme
	getTheme(): string {
		const html = document.documentElement;
		return html.getAttribute('data-theme') || 'dark';
	}
}
