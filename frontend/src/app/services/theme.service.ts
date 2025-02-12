import { Injectable } from '@angular/core';

@Injectable({
	providedIn: 'root'
})
export class ThemeService {

	constructor() { }
	
	setTheme(theme: string): void {
		const html = document.documentElement;
		html.setAttribute('data-theme', theme); 
	}

	getTheme(): string {
		const html = document.documentElement;
		return html.getAttribute('data-theme') || 'dark';
	}
}
