import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../model/user.type';
import { LoginReqest } from '../../model/loginReqest.type';
import { Router } from '@angular/router';

@Injectable({
	providedIn: 'root'
})
export class UserService {
	token = "";
	constructor(private http: HttpClient, private router: Router) { }
	registerUser(user: User): void {
		console.log(user)
		this.http.post('http://localhost:8080/auth/register', user, { responseType: 'text' })
			.subscribe(response => {
				console.info(`User registration response: ${response}`);
				this.router.navigate(['/login']);
			}, error => {
				console.error('Registration error:', error);
			});
	}

	loginUser(login: LoginReqest): void {
		this.http.post('http://localhost:8080/auth/login', login, { responseType: 'text' })
			.subscribe(tokenResponce => { 
				this.token = tokenResponce; 
				console.info(`User login response: ${tokenResponce}`); 
				this.router.navigate(['/dashboard']);
			}, error => {
				console.error('Login error:', error);
			});
	}
}
