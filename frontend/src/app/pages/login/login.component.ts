import { NgClass } from '@angular/common';
import { Component, computed, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { LoginReqest } from '../../../model/loginReqest.type';

@Component({
	selector: 'app-login',
	imports: [RouterLink, NgClass, FormsModule],
	templateUrl: './login.component.html'
})
export class LoginComponent {
	constructor(private userService: UserService) { }
	username = signal<string>('');
	password = signal<string>('');
	loginReqest = computed<LoginReqest>(() => ({
		username: this.username(),
		password: this.password(),
	}));

	isValidUsername = signal<boolean>(true);
	isValidPassword = signal<boolean>(true);

	validateForm() {
		this.validateUsername();
		this.validatePassword();
		return this.isValidPassword() && this.isValidUsername();
	}

	validateUsername() {
		this.isValidUsername.set(this.username() != '');
	}

	validatePassword() {
		this.isValidPassword.set(this.password() != '');
	}

	loginUser() {
		var isFormValid = this.validateForm()
		if (isFormValid) {
			this.userService.loginUser(this.loginReqest())
		}
	}
}
