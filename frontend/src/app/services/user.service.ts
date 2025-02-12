import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user.type';
import { LoginReqest } from '../../model/loginReqest.type';
import { Router } from '@angular/router';

@Injectable({
	providedIn: 'root'
})
export class UserService {
	constructor(private http: HttpClient, private router: Router) { }
	registerUser(user: User): void {
		console.log(user)
		this.http.post('http://localhost:8080/auth/register', user, { responseType: 'text' })
			.subscribe({
				complete: () => this.router.navigate(['/login']),
				error: (error) => console.error('Registration error:', error)
			});
	}

	loginUser(login: LoginReqest): void {
		this.http.post('http://localhost:8080/auth/login', login, { responseType: 'text' })
			.subscribe({
				next: (tokenResponce) => sessionStorage.setItem('token', tokenResponce),
				complete: () => this.router.navigate(['/dashboard']),
				error: (error) => console.error('Login error:', error)
			});
	}
}
